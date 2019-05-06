package co.edu.ucatolica.clustering.front.api.model;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import co.edu.ucatolica.clustering.front.api.serializer.ClusteringResponseDeserializer;

@JsonDeserialize(using = ClusteringResponseDeserializer.class)
public class AbstractClusteringMethodResponse {
	
	@JsonProperty("id")
	protected String executionId;
	
	protected String methodName;
	
	protected String methodId;
	
	protected String dataId;
	
	protected Date executionDate;
	
	protected Map<String, String> execParams;

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodId() {
		return methodId;
	}

	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public Date getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}

	public Map<String, String> getExecParams() {
		return execParams;
	}

	public void setExecParams(Map<String, String> execParams) {
		this.execParams = execParams;
	}

}
