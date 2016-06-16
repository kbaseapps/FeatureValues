
package us.kbase.kbasefeaturevalues;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: ServiceStatus</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "version",
    "status",
    "startup_time",
    "giturl",
    "branch",
    "commit",
    "deployment_cfg_path",
    "safe_configuration"
})
public class ServiceStatus {

    @JsonProperty("version")
    private java.lang.String version;
    @JsonProperty("status")
    private java.lang.String status;
    @JsonProperty("startup_time")
    private java.lang.String startupTime;
    @JsonProperty("giturl")
    private java.lang.String giturl;
    @JsonProperty("branch")
    private java.lang.String branch;
    @JsonProperty("commit")
    private java.lang.String commit;
    @JsonProperty("deployment_cfg_path")
    private java.lang.String deploymentCfgPath;
    @JsonProperty("safe_configuration")
    private Map<String, String> safeConfiguration;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("version")
    public java.lang.String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(java.lang.String version) {
        this.version = version;
    }

    public ServiceStatus withVersion(java.lang.String version) {
        this.version = version;
        return this;
    }

    @JsonProperty("status")
    public java.lang.String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(java.lang.String status) {
        this.status = status;
    }

    public ServiceStatus withStatus(java.lang.String status) {
        this.status = status;
        return this;
    }

    @JsonProperty("startup_time")
    public java.lang.String getStartupTime() {
        return startupTime;
    }

    @JsonProperty("startup_time")
    public void setStartupTime(java.lang.String startupTime) {
        this.startupTime = startupTime;
    }

    public ServiceStatus withStartupTime(java.lang.String startupTime) {
        this.startupTime = startupTime;
        return this;
    }

    @JsonProperty("giturl")
    public java.lang.String getGiturl() {
        return giturl;
    }

    @JsonProperty("giturl")
    public void setGiturl(java.lang.String giturl) {
        this.giturl = giturl;
    }

    public ServiceStatus withGiturl(java.lang.String giturl) {
        this.giturl = giturl;
        return this;
    }

    @JsonProperty("branch")
    public java.lang.String getBranch() {
        return branch;
    }

    @JsonProperty("branch")
    public void setBranch(java.lang.String branch) {
        this.branch = branch;
    }

    public ServiceStatus withBranch(java.lang.String branch) {
        this.branch = branch;
        return this;
    }

    @JsonProperty("commit")
    public java.lang.String getCommit() {
        return commit;
    }

    @JsonProperty("commit")
    public void setCommit(java.lang.String commit) {
        this.commit = commit;
    }

    public ServiceStatus withCommit(java.lang.String commit) {
        this.commit = commit;
        return this;
    }

    @JsonProperty("deployment_cfg_path")
    public java.lang.String getDeploymentCfgPath() {
        return deploymentCfgPath;
    }

    @JsonProperty("deployment_cfg_path")
    public void setDeploymentCfgPath(java.lang.String deploymentCfgPath) {
        this.deploymentCfgPath = deploymentCfgPath;
    }

    public ServiceStatus withDeploymentCfgPath(java.lang.String deploymentCfgPath) {
        this.deploymentCfgPath = deploymentCfgPath;
        return this;
    }

    @JsonProperty("safe_configuration")
    public Map<String, String> getSafeConfiguration() {
        return safeConfiguration;
    }

    @JsonProperty("safe_configuration")
    public void setSafeConfiguration(Map<String, String> safeConfiguration) {
        this.safeConfiguration = safeConfiguration;
    }

    public ServiceStatus withSafeConfiguration(Map<String, String> safeConfiguration) {
        this.safeConfiguration = safeConfiguration;
        return this;
    }

    @JsonAnyGetter
    public Map<java.lang.String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(java.lang.String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public java.lang.String toString() {
        return ((((((((((((((((((("ServiceStatus"+" [version=")+ version)+", status=")+ status)+", startupTime=")+ startupTime)+", giturl=")+ giturl)+", branch=")+ branch)+", commit=")+ commit)+", deploymentCfgPath=")+ deploymentCfgPath)+", safeConfiguration=")+ safeConfiguration)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
