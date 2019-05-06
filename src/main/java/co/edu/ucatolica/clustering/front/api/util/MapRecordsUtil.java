 package co.edu.ucatolica.clustering.front.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;

public class MapRecordsUtil {
	
	
	public static List<String> getHeaderFromMap(Map<String, String> records){
		
		checkMapForEmpty(records);
		return new ArrayList<String>(records.keySet());
	}
	
	public static List<String> getRecordFromMap(Map<String, String> records, List<String> headers){
		
		checkMapForEmpty(records);
		checkListForEmpty(headers);
		
		final List<String> recordList = new ArrayList<>();
		
		headers
		.forEach(value -> recordList.add(records.get(value)));
		
		return recordList;
	}
	
	public static List<List<String>> mapRecordToRecordList(Map<String, String> mapRecords){
		
		checkMapForEmpty(mapRecords);
		
		final List<List<String>> records= new ArrayList<>();
		
		mapRecords
		.forEach((key, value) -> records.add(Arrays.asList(key,value)));
		
		return records;
		
	}
	
	public static List<List<String>> listToRecordList(List<String> listRecords){
		
		checkListForEmpty(listRecords);
		
		final List<List<String>> records= new ArrayList<>();
		
		listRecords
		.forEach( value -> records.add(Arrays.asList(value)));
		
		return records;
		
	}
	
	private static void checkListForEmpty(List<String> list) {
		
		if(list.isEmpty()) {
			throw new IllegalArgumentException("la lista no debe ser vacia");
		}
	}
	
	private static void checkMapForEmpty(Map<String, String> map) {
		
		if(map.isEmpty() || !ObjectUtils.anyNotNull(map)) {
			
			throw new IllegalArgumentException("el mapa no debe ser null o vacio");
		}
	}

}
