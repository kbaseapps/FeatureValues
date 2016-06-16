package us.kbase.kbasefeaturevalues.transform;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.management.ObjectName;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import us.kbase.auth.AuthService;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.UObject;
import us.kbase.kbasefeaturevalues.ExpressionMatrix;
import us.kbase.kbasefeaturevalues.FloatMatrix2D;
import us.kbase.kbasefeaturevalues.MatrixUtil;
import us.kbase.workspace.WorkspaceClient;

public class ExpressionUploader {
    private static Pattern tabDiv = Pattern.compile(Pattern.quote("\t"));
    public static final String FORMAT_TYPE_MO = "MO";
    public static final String FORMAT_TYPE_SIMPLE = "Simple";
    
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
        AuthToken token = null;
        if (parsedArgs.goName != null) {
            String user = System.getProperty("test.user");
            String pwd = System.getProperty("test.pwd");
            String tokenString = System.getenv("KB_AUTH_TOKEN");
            token = tokenString == null ? AuthService.login(user, pwd).getToken() :
                new AuthToken(tokenString);
        }
        File inputFile = findTabFile(parsedArgs.inDir);
        ExpressionMatrix matrix = parse(parsedArgs.wsUrl, parsedArgs.wsName, inputFile, 
                parsedArgs.fmtType, parsedArgs.goName, parsedArgs.fillMissingValues, 
                parsedArgs.dataType, parsedArgs.dataScale, token);
        String outputFileName = parsedArgs.outName;
        if (outputFileName == null)
            outputFileName = "expression_output.json";
        File workDir = parsedArgs.workDir;
        if (workDir == null)
            workDir = new File(".");
        if (!workDir.exists())
            workDir.mkdirs();
        File outputFile = new File(workDir, outputFileName);
        UObject.getMapper().writeValue(outputFile, matrix);
    }

    public static File findTabFile(File inputDir) {
        File inputFile = null;
        StringBuilder fileList = new StringBuilder();
        for (File f : inputDir.listFiles()) {
            if (!f.isFile())
                continue;
            String fileName = f.getName().toLowerCase();
            if (fileName.endsWith(".txt") || fileName.endsWith(".tsv") || 
                    fileName.endsWith(".csv") || fileName.endsWith(".tab")) {
                inputFile = f;
                break;
            }
            if (fileList.length() > 0)
                fileList.append(", ");
            fileList.append(f.getName());
        }
        if (inputFile == null)
            throw new IllegalStateException("Input file with extention .txt or .tsv was not " +
            		"found among: " + fileList);
        return inputFile;
    }
    
    public static ExpressionMatrix parse(String wsUrl, String wsName, File inputFile, 
            String fmtType, String goName, boolean fillMissingValues, String dataType, 
            String dataScale, AuthToken token) throws Exception {
        Map<String, Object> genome = null;
        if (goName != null) {
            WorkspaceClient cl = getWsClient(wsUrl, token);
            genome = MatrixUtil.loadGenomeFeatures(cl, wsName + "/" + goName);
        }
        String formatType = fmtType;
        if (formatType == null || formatType.trim().isEmpty())
            formatType = FORMAT_TYPE_SIMPLE;
        ExpressionMatrix matrix = null;
        if (formatType.equalsIgnoreCase(FORMAT_TYPE_MO)) {
            matrix = parseMicrobsOnlineFormat(new BufferedReader(new FileReader(inputFile)));
        } else if (formatType.equalsIgnoreCase(FORMAT_TYPE_SIMPLE)) {
            matrix = parseSimpleFormat(new BufferedReader(new FileReader(inputFile)));
        } else {
            throw new IllegalStateException("Unsupported format type: " + formatType);
        }
        if (dataType != null && !dataType.isEmpty())
            matrix.withType(dataType);
        if (dataScale != null && !dataScale.isEmpty())
            matrix.withScale(dataScale);
        if (genome != null) {
            matrix.withFeatureMapping(MatrixUtil.constructFeatureMapping(matrix.getData(), genome));
            matrix.withGenomeRef(wsName + "/" + goName);
        }
        if (fillMissingValues)
            MatrixUtil.fillMissingValues(matrix.getData());
        return matrix;
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
        out.println("Program uploads expression data in different formats.");
        out.println("Usage: <program> [options...]");
        out.println("   Or: <program> {-h|--help}  - to see this help");
        parser.printUsage(out);
    }

    public static ExpressionMatrix parseMicrobsOnlineFormat(BufferedReader br) throws Exception {
        MOState state = MOState.init;
        Map<String, String> colNameToId = new LinkedHashMap<String, String>();
        List<String> colIds = new ArrayList<String>();
        List<String> rowIds = new ArrayList<String>();
        List<List<Double>> values = new ArrayList<List<Double>>();
        for (int pos = 0; ; pos++) {
            String line = br.readLine();
            if (line == null)
                break;
            switch (state) {
            case init:
                if (line.startsWith("^") && line.endsWith("Experiment MetaData")) {
                    state = MOState.condHeader;
                } else {
                    throw new IllegalStateException("Unexpected line for [" + state + "] state: " + line);
                }
                break;
            case condHeader:
                if (line.startsWith("!Log Level")) {
                    state = MOState.condBlock;
                } else {
                    throw new IllegalStateException("Unexpected line for [" + state + "] state: " + line);
                }
                break;
            case condBlock:
                if (line.startsWith("^") && line.endsWith("Data Section")) {
                    state = MOState.featHeader;
                } else if (line.startsWith("!")) {
                    String[] parts = tabDiv.split(line.substring(1));
                    String colName = parts[0] + " (" + parts[1] + ")";
                    String colId = parts[2];
                    if (!colId.contains(parts[1]))
                        colId += " (" + parts[1] + ")";
                    colNameToId.put(colName, colId);
                } else {
                    throw new IllegalStateException("Unexpected line for [" + state + "] state: " + line);
                }
                break;
            case featHeader:
                if (line.startsWith("!Locus Id\tSystematic_Name\t")) {
                    String[] parts = tabDiv.split(line);
                    for (int i = 2; i < parts.length; i++) {
                        String colName = parts[i];
                        String colId = colNameToId.get(colName);
                        if (colId == null)
                            throw new IllegalStateException("Id wasn't found for column with name: " + colName);
                        colIds.add(colId);
                    }
                    state = MOState.featBlock;
                } else {
                    throw new IllegalStateException("Unexpected line for [" + state + "] state: " + line);
                }
                break;
            case featBlock:
                String[] parts = tabDiv.split(line, -1);
                int partCount = parts.length;
                if (partCount > colIds.size() + 2 && parts[partCount - 1].isEmpty())
                    partCount--;
                if (partCount != colIds.size() + 2)
                    throw new IllegalStateException("Feature row contain " + parts.length + " != " +  
                            (colIds.size() + 2) + " cells at line " + (pos + 1) + ": " + line);
                String rowId = parts[1];
                int rowPos = rowIds.size();
                rowIds.add(rowId);
                if (values.size() != rowPos)
                    throw new IllegalStateException("Internal inconsistency: " + rowPos + " != " + values.size());
                List<Double> row = new ArrayList<Double>();
                values.add(row);
                for (int i = 0; i < colIds.size(); i++) {
                    String exprValueText = parts[2 + i];
                    Double exprVallue = exprValueText.isEmpty() ? null : Double.parseDouble(exprValueText);
                    row.add(exprVallue);
                }                    
                break;
            default:
                throw new IllegalStateException("Unsupported state: " + state);
            }
        }
        br.close();
        FloatMatrix2D matrix = new FloatMatrix2D().withValues(values).withColIds(colIds)
                .withRowIds(rowIds);
        ExpressionMatrix ret = new ExpressionMatrix().withType("log-ratio").withScale("1.0")
                .withData(matrix);
        return ret;
    }

    public static ExpressionMatrix parseSimpleFormat(BufferedReader br) throws Exception {
        List<String> colIds = new ArrayList<String>();
        String line = br.readLine();
        if (line == null)
            throw new IllegalStateException("No header line found");
        String[] parts = tabDiv.split(line);
        for (int i = 1; i < parts.length; i++)
            colIds.add(unquote(parts[i]));
        List<String> rowIds = new ArrayList<String>();
        List<List<Double>> values = new ArrayList<List<Double>>();
        for (int pos = 0; ; pos++) {
            line = br.readLine();
            if (line == null)
                break;
            parts = tabDiv.split(line, -1);
            int partCount = parts.length;
            if (partCount > colIds.size() + 1 && parts[partCount - 1].isEmpty())
                partCount--;
            if (partCount != colIds.size() + 1)
                throw new IllegalStateException("Feature row contain " + parts.length + " != " +  
                        (colIds.size() + 1) + " cells at line " + (pos + 1) + ": " + line);
            String rowId = parts[0];
            int rowPos = rowIds.size();
            rowIds.add(unquote(rowId));
            if (values.size() != rowPos)
                throw new IllegalStateException("Internal inconsistency: " + rowPos + " != " + values.size());
            List<Double> row = new ArrayList<Double>();
            values.add(row);
            for (int i = 0; i < colIds.size(); i++) {
                String exprValueText = parts[1 + i];
                Double exprVallue = isMissingValue(exprValueText) ? null : Double.parseDouble(exprValueText);
                row.add(exprVallue);
            }
        }
        br.close();
        FloatMatrix2D matrix = new FloatMatrix2D().withValues(values).withColIds(colIds)
                .withRowIds(rowIds);
        ExpressionMatrix ret = new ExpressionMatrix().withType("log-ratio").withScale("1.0")
                .withData(matrix);
        return ret;
    }

    private static boolean isMissingValue(String value) {
        value = unquote(value);
        return value.isEmpty() || value.equalsIgnoreCase("NA") || value.equalsIgnoreCase("NULL") ||
                value.equals("-");
    }

    private static String unquote(String value) {
        if (value.length() >= 2 && value.charAt(0) == '\"' && 
                value.charAt(value.length() - 1) == '\"') {
            try {
                value = ObjectName.unquote(value);
            } catch (Exception ex) {
                value = value.substring(1, value.length() - 1);
            }
        }
        return value;
    }
    
    public static class Args {
        @Option(name="-ws", aliases={"--workspace_service_url"}, usage="Workspace service URL", metaVar="<ws-url>")
        String wsUrl;
        
        @Option(name="-wn", aliases={"--workspace_name"}, usage="Workspace name", metaVar="<ws-name>")
        String wsName;

        @Option(name="-on", aliases={"--object_name"}, usage="Object name", metaVar="<obj-name>")
        String objName;

        @Option(name="-of", aliases={"--output_file_name"}, usage="Output file name", metaVar="<out-file>")
        String outName;

        @Option(name="-id", aliases={"--input_directory"}, usage="Input directory", metaVar="<in-dir>")
        File inDir;

        @Option(name="-wd", aliases={"--working_directory"}, usage="Working directory", metaVar="<work-dir>")
        File workDir;

        @Option(name="-im", aliases={"--input_mapping"}, usage="Input mapping", metaVar="<in-map>")
        String inMap;

        @Option(name="-ft", aliases={"--format_type"}, usage="Format type", metaVar="<fmt-type>")
        String fmtType;

        @Option(name="-gn", aliases={"--genome_object_name"}, usage="Input mapping", metaVar="<gnm-name>")
        String goName;
        
        @Option(name="-fm", aliases={"--fill_missing_values"}, usage="Fill missing values (boolean, default value is false)")
        boolean fillMissingValues = false;        

        @Option(name="-dt", aliases={"--data_type"}, usage="Data type (default is 'log-ratio')", metaVar="<data-type>")
        String dataType;

        @Option(name="-ds", aliases={"--data_scale"}, usage="Data scale (default is '1.0')", metaVar="<data-scale>")
        String dataScale;
    }
    
    enum MOState {
        init, condHeader, condBlock, featHeader, featBlock;
    };
}
