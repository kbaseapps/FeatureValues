package us.kbase.kbasefeaturevalues.transform.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.management.ObjectName;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import us.kbase.auth.AuthException;
import us.kbase.auth.AuthService;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.UObject;
import us.kbase.common.utils.ProcessHelper;
import us.kbase.kbasefeaturevalues.ExpressionMatrix;
import us.kbase.kbasefeaturevalues.KBaseFeatureValuesServer;
import us.kbase.kbasefeaturevalues.transform.ExpressionUploader;
import us.kbase.workspace.CreateWorkspaceParams;
import us.kbase.workspace.ObjectSaveData;
import us.kbase.workspace.SaveObjectsParams;
import us.kbase.workspace.WorkspaceClient;
import us.kbase.workspace.WorkspaceIdentity;

public class ExpressionDownloaderTest {
    private static AuthToken token = null;
    private static File workDir = null;
    private static String testWsName = null;
    private static File downloadCLI = null;

    @BeforeClass
    public static void beforeClass() throws Exception {
        workDir = prepareWorkDir("uploader");
        token = getToken();
        downloadCLI = new File(workDir, "upload.sh");
        String jarsDir = new File("../jars/lib/jars").getCanonicalPath() + "/";
        String classPath = new File(".").getCanonicalPath() + "/dist/KBaseFeatureValues.jar" +
                ":" + jarsDir + "jackson/jackson-annotations-2.2.3.jar" + 
                ":" + jarsDir + "jackson/jackson-core-2.2.3.jar" + 
                ":" + jarsDir + "jackson/jackson-databind-2.2.3.jar" + 
                ":" + jarsDir + "kbase/auth/kbase-auth-1398468950-3552bb2.jar" + 
                ":" + jarsDir + "kbase/common/kbase-common-0.0.10.jar" + 
                ":" + jarsDir + "kbase/workspace/WorkspaceClient-0.2.0.jar" + 
                ":" + jarsDir + "kohsuke/args4j-2.0.21.jar";
        writeFileLines(Arrays.asList(
                "#!/bin/bash",
                "export KB_AUTH_TOKEN=\"" + token.toString() + "\"",
                "java -cp " + classPath + " us.kbase.kbasefeaturevalues.transform.ExpressionDownloader $*"
                ), downloadCLI);
        ProcessHelper.cmd("chmod", "a+x", downloadCLI.getAbsolutePath()).exec(workDir);
        /// Temporary workspace
        String machineName = java.net.InetAddress.getLocalHost().getHostName();
        machineName = machineName == null ? "nowhere" : machineName.toLowerCase().replaceAll("[^\\dA-Za-z_]|\\s", "_");
        long suf = System.currentTimeMillis();
        WorkspaceClient wscl = getWsClient();
        Exception error = null;
        for (int i = 0; i < 5; i++) {
            testWsName = "test_feature_values_" + machineName + "_" + suf;
            try {
                wscl.createWorkspace(new CreateWorkspaceParams().withWorkspace(testWsName));
                error = null;
                break;
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                error = ex;
            }
        }
        if (error != null)
            throw error;
    }

    @AfterClass
    public static void afterClass() throws Exception {
        try {
            if (testWsName != null) {
                getWsClient().deleteWorkspace(new WorkspaceIdentity().withWorkspace(testWsName));
                //System.out.println("Test workspace " + testWsName + " was deleted");
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    public void testDownloader1() throws Exception {
        WorkspaceClient wscl = getWsClient();
        String exprObjName = "Desulfovibrio_vulgaris_Hildenborough.expression";
        File inputDir = new File("test/data/upload2");
        File exprInputFile = ExpressionUploader.findTabFile(inputDir);
        ExpressionMatrix matrix = ExpressionUploader.parse(null, null, exprInputFile, "Simple", 
                null, false, null, null, null);
        wscl.saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(exprObjName).withType("KBaseFeatureValues.ExpressionMatrix")
                .withData(new UObject(matrix)))));
        File tsvTempFile = new File(workDir, "matrix.tsv");
        ProcessHelper.cmd("bash", downloadCLI.getAbsolutePath(), 
                "--workspace_service_url", getWsUrl(), 
                "--workspace_name", testWsName, 
                "--object_name", exprObjName, 
                "--working_directory", tsvTempFile.getParent(), 
                "--output_file_name", tsvTempFile.getName()).exec(workDir);
        List<String> lines = readFileLines(tsvTempFile);
        Assert.assertEquals(12, lines.get(0).split(Pattern.quote("\t")).length);
        Assert.assertEquals(2681, lines.size());
    }

    private static WorkspaceClient getWsClient() throws Exception {
        WorkspaceClient wscl = new WorkspaceClient(new URL(getWsUrl()), token);
        wscl.setAuthAllowedForHttp(true);
        return wscl;
    }

    private static String getWsUrl() {
        return systemGetProperty("test." + KBaseFeatureValuesServer.CONFIG_PARAM_WS_URL);
    }

    private static AuthToken getToken() throws AuthException, IOException {
        String user = systemGetProperty("test.user");
        String password = systemGetProperty("test.password");
        AuthToken token = AuthService.login(user, password).getToken();
        return token;
    }

    private static void writeFileLines(List<String> lines, File targetFile) throws IOException {
        PrintWriter pw = new PrintWriter(targetFile);
        for (String l : lines)
            pw.println(l);
        pw.close();
    }

    private static List<String> readFileLines(File file) throws IOException {
        List<String> ret = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            while (true) {
                String l = br.readLine();
                if (l == null)
                    break;
                ret.add(l);
            }
            return ret;
        } finally {
            br.close();
        }
    }

    private static String systemGetProperty(String name) {
        String ret = System.getProperty(name);
        if (ret == null)
            throw new IllegalStateException("Parameter " + name + " is not defined " +
                    "in test configuration");
        return ret;
    }
    
    private static File prepareWorkDir(String testName) throws IOException {
        File tempDir = new File(systemGetProperty("test.temp-dir")).getCanonicalFile();
        if (!tempDir.exists())
            tempDir.mkdirs();
        for (File dir : tempDir.listFiles()) {
            if (dir.isDirectory() && dir.getName().startsWith("test_" + testName + "_"))
                try {
                    deleteRecursively(dir);
                } catch (Exception e) {
                    System.out.println("Can not delete directory [" + dir.getName() + "]: " + e.getMessage());
                }
        }
        File workDir = new File(tempDir, "test_" + testName + "_" + System.currentTimeMillis());
        if (!workDir.exists())
            workDir.mkdir();
        return workDir;
    }
    
    private static void deleteRecursively(File fileOrDir) {
        if (fileOrDir.isDirectory() && !Files.isSymbolicLink(fileOrDir.toPath()))
            for (File f : fileOrDir.listFiles()) 
                deleteRecursively(f);
        fileOrDir.delete();
    }

}
