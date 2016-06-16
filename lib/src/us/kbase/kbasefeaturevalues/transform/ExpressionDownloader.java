package us.kbase.kbasefeaturevalues.transform;

import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import us.kbase.auth.AuthService;
import us.kbase.auth.AuthToken;
import us.kbase.kbasefeaturevalues.FloatMatrix2D;
import us.kbase.workspace.ObjectIdentity;
import us.kbase.workspace.WorkspaceClient;

public class ExpressionDownloader {
    
    public static void main(String[] args) throws Exception {
        Args parsedArgs = new Args();
        CmdLineParser parser = new CmdLineParser(parsedArgs);
        parser.setUsageWidth(100);
        if (args.length == 0 || (args.length == 1 && (args[0].equals("-h") || 
                args[0].equals("--help")))) {
            parser.parseArgument();
            showUsage(parser, null, System.out);
            return;
        }
        try {
            parser.parseArgument(args);
        } catch (CmdLineException ex) {
            String message = ex.getMessage();
            showUsage(parser, message);
            return;
        }
        String user = System.getProperty("test.user");
        String pwd = System.getProperty("test.pwd");
        String tokenString = System.getenv("KB_AUTH_TOKEN");
        AuthToken token = tokenString == null ? AuthService.login(user, pwd).getToken() :
            new AuthToken(tokenString);
        String outputFileName = parsedArgs.outName;
        if (outputFileName == null)
            outputFileName = "matrix.tsv";
        File outputFile = new File(parsedArgs.workDir, outputFileName);
        generate(parsedArgs.wsUrl, parsedArgs.wsName, parsedArgs.objName, parsedArgs.version, 
                token, new PrintWriter(outputFile));
    }
    
    public static void generate(String wsUrl, String wsName, String objName, Integer version,
            AuthToken token, PrintWriter pw) throws Exception {
        BioMatrix matrix = null;
        try {
            WorkspaceClient client = getWsClient(wsUrl, token);
            String ref = wsName + "/" + objName;
            if (version != null)
                ref += "/" + version;
            matrix = client.getObjects(Arrays.asList(new ObjectIdentity().withRef(ref)))
                    .get(0).getData().asClassInstance(BioMatrix.class);
        } finally {
            pw.close();
        }
        if (matrix != null)
            generate(matrix.getData(), matrix.getFeatureMapping(), pw);
    }

    public static void generate(FloatMatrix2D data, Map<String, String> featureMapping, PrintWriter pw) throws Exception {
        try {
            pw.print("feature_ids");
            for (String condId : data.getColIds())
                pw.print("\t" + condId);
            pw.println();
            for (int rowPos = 0; rowPos < data.getRowIds().size(); rowPos++) {
                String rowId = data.getRowIds().get(rowPos);
                String featureId = featureMapping == null ? null :
                    featureMapping.get(rowId);
                if (featureId == null)
                    featureId = rowId;
                pw.print(featureId);
                for (Double value : data.getValues().get(rowPos)) {
                    pw.print("\t");
                    if (value != null)
                        pw.print(value);
                }
                pw.println();
            }
        } finally {
            pw.close();
        }
    }

    private static WorkspaceClient getWsClient(String wsUrl, AuthToken token) throws Exception {
        WorkspaceClient wsClient = new WorkspaceClient(new URL(wsUrl), token);
        wsClient.setAuthAllowedForHttp(true);
        return wsClient;
    }

    private static void showUsage(CmdLineParser parser, String message) {
        showUsage(parser, message, System.err);
    }
    
    private static void showUsage(CmdLineParser parser, String message, PrintStream out) {
        if (message != null)
            out.println(message);
        out.println("Program downloads expression data in TSV format.");
        out.println("Usage: <program> [options...]");
        out.println("   Or: <program> {-h|--help}  - to see this help");
        parser.printUsage(out);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BioMatrix {
        @JsonProperty("feature_mapping")
        private Map<String, String> featureMapping;
        @JsonProperty("data")
        private FloatMatrix2D data;
        private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();
        
        @JsonProperty("feature_mapping")
        public Map<String, String> getFeatureMapping() {
            return featureMapping;
        }

        @JsonProperty("feature_mapping")
        public void setFeatureMapping(Map<String, String> featureMapping) {
            this.featureMapping = featureMapping;
        }

        @JsonProperty("data")
        public FloatMatrix2D getData() {
            return data;
        }

        @JsonProperty("data")
        public void setData(FloatMatrix2D data) {
            this.data = data;
        }

        @JsonAnyGetter
        public Map<java.lang.String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperties(java.lang.String name, Object value) {
            this.additionalProperties.put(name, value);
        }
    }

    public static class Args {
        @Option(name="-ws", aliases={"--workspace_service_url"}, usage="Workspace service URL", metaVar="<ws-url>")
        String wsUrl;
        
        @Option(name="-wn", aliases={"--workspace_name"}, usage="Workspace name", metaVar="<ws-name>")
        String wsName;

        @Option(name="-on", aliases={"--object_name"}, usage="Object name", metaVar="<obj-name>")
        String objName;

        @Option(name="-ov", aliases={"--version"}, usage="Object version (optional)", metaVar="<obj-ver>")
        Integer version;

        @Option(name="-wd", aliases={"--working_directory"}, usage="Working directory", metaVar="<work-dir>")
        File workDir;

        @Option(name="-of", aliases={"--output_file_name"}, usage="Output file name", metaVar="<out-file>")
        String outName;
    }

}
