package co.edu.ucatolica.clustering.front.api.serializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import co.edu.ucatolica.clustering.front.api.model.AbstractClusteringMethodResponse;
import co.edu.ucatolica.clustering.front.api.model.AgnesResponse;
import co.edu.ucatolica.clustering.front.api.model.ClaraResponse;
import co.edu.ucatolica.clustering.front.api.model.ClaraResult;
import co.edu.ucatolica.clustering.front.api.model.DianaResponse;
import co.edu.ucatolica.clustering.front.api.model.HierarchicalResult;
import co.edu.ucatolica.clustering.front.api.model.KmeansResponse;
import co.edu.ucatolica.clustering.front.api.model.KmeansResult;
import co.edu.ucatolica.clustering.front.api.model.PamResponse;
import co.edu.ucatolica.clustering.front.api.model.PamResult;

public class ClusteringResponseDeserializer extends JsonDeserializer<AbstractClusteringMethodResponse> {
	
	 
	private static final String KMEANS="KMEANS";
	
	private static final String AGNES="AGNES";
	
	private static final String DIANA="DIANA";
	
	private static final String PAM="PAM";
	
	private static final String CLARA="CLARA";
	
	@Override
	public AbstractClusteringMethodResponse deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);
		
		final AbstractClusteringMethodResponse response = getMethodResult(node);
		completeMethodData(response,oc,node);
		
		return response;
	    
	}
  
	private AbstractClusteringMethodResponse getMethodResult(JsonNode node) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
		String partialResult = node.get("executionData").get("result").get("description").asText().replaceAll("\n","");
		JsonNode partialNode = mapper.readValue(partialResult, JsonNode.class);

		final String methodName = node.get("methodName").asText();
		AbstractClusteringMethodResponse response = null;
		
		switch (methodName) {
	
			case KMEANS:
				
				KmeansResponse kmeansResponse = new KmeansResponse();
				KmeansResult result = new KmeansResult();
				//partialNode.get("centers");
				//partialNode.get("clusters");
				List<Map<String, String>> centers = mapper.readValue(partialNode.get("centers").asText() ,  new TypeReference<List<Map<String, String>>>(){});
				Map<String,String> clusters =mapper.readValue(partialNode.get("clusters").asText() , new TypeReference<Map<String, String>>(){});
				KmeansResult kmeansResult = new KmeansResult();
				kmeansResult.setCenters(centers);
				kmeansResult.setClusters(clusters);
				kmeansResponse.setResult(kmeansResult);
				
				response = kmeansResponse;
				break;
			
			case AGNES:
				
				AgnesResponse agnesResponse = new AgnesResponse();
				//HierarchicalResult agnesResult = oc.readValue(resultParser, HierarchicalResult.class);
				//agnesResponse.setResult(agnesResult);
				
				response = agnesResponse;
				break;
				
			case DIANA:
				
				DianaResponse dianaResponse = new DianaResponse();
				//HierarchicalResult dianaResult = oc.readValue(resultParser, HierarchicalResult.class);
				//dianaResponse.setResult(dianaResult);
				
				response = dianaResponse;
				break;
				
			case PAM:
				
				PamResponse pamResponse = new PamResponse();
				//PamResult pamResult = oc.readValue(resultParser, PamResult.class);
				//pamResponse.setResult(pamResult);
				
				response = pamResponse;
				break;
				
			case CLARA:
				
				ClaraResponse claraResponse = new ClaraResponse();
				//ClaraResult claraResult = oc.readValue(resultParser, ClaraResult.class);
				//claraResponse.setResult(claraResult);
				
				response = claraResponse;
				break;
		}
		
		if(!ObjectUtils.anyNotNull(response)) {
			response.setMethodName(methodName);
		}
		
		return response;
	}
	
	private void completeMethodData(AbstractClusteringMethodResponse method, ObjectCodec oc, JsonNode node) throws IOException {
		
		JsonParser execParamsParser = node.get("execParams").traverse();
		Map<String, String> execParams = new HashMap<>();
		execParams = oc.readValue(execParamsParser, new TypeReference<Map<String, String>>(){});
		
		if(execParams.isEmpty()) {
			throw new IllegalArgumentException("error al deserializar los parametros de ejecuciòn");
		}
		
		try {
			method.setExecutionDate(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(node.get("executionDate").asText()));
		} catch (ParseException e) {
			throw new IllegalArgumentException("error al deserializar la fecha de ejecucion");
		}
				
		method.setExecParams(execParams);
		method.setDataId(node.get("dataId").asText());
		method.setExecutionId(node.get("id").asText());
		method.setMethodId(node.get("methodId").asText());
	}
}