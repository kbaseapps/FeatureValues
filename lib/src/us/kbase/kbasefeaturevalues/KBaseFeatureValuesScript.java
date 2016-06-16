package us.kbase.kbasefeaturevalues;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import us.kbase.common.service.UObject;
import us.kbase.common.utils.TextUtils;
import us.kbase.userandjobstate.InitProgress;
import us.kbase.userandjobstate.Results;
import us.kbase.userandjobstate.UserAndJobStateClient;

public class KBaseFeatureValuesScript {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Throwable {
        if (args.length != 1) {
            System.out.println("Usage: <program> <hex-args-string>");
            System.exit(1);
        }
        String token = System.getenv("KB_AUTH_TOKEN");
        Map<String, Object> argsMap = UObject.getMapper().readValue(
                TextUtils.hexToString(args[0]), Map.class);
        String methodName = (String)argsMap.get("method");
        String jobId = (String)argsMap.get("jobid");
        List<Object> params = (List<Object>)argsMap.get("params");
        Map<String, String> config = (Map<String, String>)argsMap.get("config");
        System.out.println("Method name: " + methodName);
        System.out.println("Job id: " + jobId);
        System.out.println("Parameters: " + params);
        System.out.println("Configuration: " + config);
        Method method = null;
        Class<?> implClass = KBaseFeatureValuesImpl.class;
        for (Method m : implClass.getMethods()) {
            if (m.getName().equals(methodName)) {
                method = m;
                break;
            }
        }
        if (method == null)
            throw new IllegalStateException("Method wasn't found: " + methodName);
        Object[] methodArgs = new Object[params.size()];
        for (int i = 0; i < params.size(); i++) {
            Class<?> targetType = method.getParameterTypes()[i];
            methodArgs[i] = UObject.transformObjectToObject(params.get(i), targetType);
        }
        KBaseFeatureValuesImpl impl = new KBaseFeatureValuesImpl(jobId, token, config, null);
        UserAndJobStateClient ujsClient = impl.getUjsClient();
        Long time = null;  //task.getEstimatedFinishTime();
        String estComplete = time == null ? null : 
            new SimpleDateFormat("YYYY-MM-DDThh:mm:ssZ").format(new Date(time));
        //ujsClient.updateJob(jobId, token, "running", estComplete);
        ujsClient.startJob(jobId, token, "running", "AWE job for " + 
                KBaseFeatureValuesServer.SERVICE_NAME + "." + methodName, 
                new InitProgress().withPtype("none"), estComplete);
        try {
            Object ret = method.invoke(impl, methodArgs);
            Results res = null;
            if (ret != null && ret instanceof String) {
                String wsRef = (String)ret;
                res = new Results().withWorkspaceurl(impl.getWsUrl())
                        .withWorkspaceids(Arrays.asList(wsRef));
            }
            ujsClient.completeJob(jobId, token, "done", null, res);
        } catch (Throwable ex) {
            if (ex instanceof InvocationTargetException) {
                InvocationTargetException ite = (InvocationTargetException)ex;
                if (ite.getCause() != null)
                    ex = ite.getCause();
            }
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            pw.close();
            String stacktrace = sw.toString();
            String status = "Error: " + ex.getMessage();
            if (status.length() > 200)
                status = status.substring(0, 197) + "...";
            ujsClient.completeJob(jobId, token, status, stacktrace, null);
        }
    }
}
