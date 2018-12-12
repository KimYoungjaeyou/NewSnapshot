package com.snapshot.es.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.snapshot.es.vo.SnapshotEsVO;

@Service
public class SnapshotEsServiceImpl implements SnapshotEsService{

	@Autowired
	private RestClient client;
	
    @Autowired
    public SnapshotEsServiceImpl(RestClient client) {
        this.client = client;
    }	
    

	
	@Override
	public List<Map<String, Object>> getGwTotalCount(String month, String frdt, String todt) {
		String url = "gw_log-" + month + "/_search";
		String querydsl = "{ \"query\" : " 													+ 
						   "{ \"bool\" : "													+
							"{ \"must\" : [ " 												+
							  "{ \"range\" : { " 											+
							   " \"reqdate\" : { "  										+
							        " \"gte\" : \"" + frdt + "\", " 				+
							        " \"lte\" : \"" + todt  + "\", "  				+
							        " \"format\" : \"yyyy-MM-dd\" " 						+
								      "}}}]}} ,"			+
								      " \"size\" : 0 ," 		+
								     "\"aggs\" : { " +
								     "\"group_by_state\" : { " +
								     "\"terms\" : { " +
								     "\"field\" : \"Controller.keyword\" } , "+
								     "\"aggs\" : { " +
								     "\"group_by_state\" : { " +
								     "\"terms\" : { " +
								     "\"field\" : \"type.keyword\" }" +
								     "}}}}}";
		System.out.println(querydsl);
		Request request = new Request("GET",url);	
		request.setJsonEntity(querydsl);			
		System.out.println("getTotalSuccessCount임!!!!!");
		
		return gwElasticsearchExec(request);
	}
	
	@Override
	public List<Map<String, Object>> getSsCount(String month, String frdt, String todt) {
	
		String url = "gw_log-" + month + "/_search";
		String querydsl = "{ \"query\" : " 													+ 
						   "{ \"bool\" : "													+
							"{ \"must\" : [ " 												+
							 "{ \"match\" : { \"Controller\" : \"snapshotSelectController\" } } , "    +
							  "{ \"match\" : { \"type\" : \"result_obj\" } } , " +
							  "{ \"range\" : { " 											+
							   " \"reqdate\" : { "  										+
							        " \"gte\" : \"" + frdt + "\", " 				+
							        " \"lte\" : \"" + todt  + "\", "  				+
							        " \"format\" : \"yyyy-MM-dd\" " 						+
							      "}}}]}} ,"			+
							      " \"size\" : 0 ," 		+
							     "\"aggs\" : { " +
							     "\"group_by_state\" : { " +
							     "\"terms\" : { " +
							     "\"field\" : \"success\" "+
							     "}}}}";
		
		System.out.println(querydsl);
		Request request = new Request("GET",url);	
		request.setJsonEntity(querydsl);			
		System.out.println("getSsCount임!!!!!");
		return ssElasticsearchExec(request);
	}

	@Override
	public List<Map<String, Object>> getSsFailCount(String month, String frdt, String todt) {

		String url = "gw_log-" + month + "/_search";
		String querydsl = "{ \"query\" : " 													+ 
						   "{ \"bool\" : "													+
							"{ \"must\" : [ " 												+
							 "{ \"match\" : { \"Controller\" : \"snapshotSelectController\" } } , "    +
							  "{ \"match\" : { \"type\" : \"result_obj\" } } , " +
							  "{ \"match\" : { \"success\" : \"false\" } } , " +
							  "{ \"range\" : { " 											+
							   " \"reqdate\" : { "  										+
							        " \"gte\" : \"" + frdt + "\", " 				+
							        " \"lte\" : \"" + todt  + "\", "  				+
							        " \"format\" : \"yyyy-MM-dd\" " 						+
							      "}}}]}} ,"			+
							      " \"size\" : 0 ," 		+
							     "\"aggs\" : { " +
							     "\"group_by_state\" : { " +
							     "\"terms\" : { " +
							     "\"field\" : \"failReason.keyword\" "+
							     "}}}}";
		
		System.out.println(querydsl);
		Request request = new Request("GET",url);	
		request.setJsonEntity(querydsl);			
		System.out.println("getSsFailCount임!!!!!");
		// TODO Auto-generated method stub
		return ssFailElasticsearchExec(request);
	}

	


	
	public List<Map<String, Object>> gwElasticsearchExec(Request request) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();


		try {
			Response response = client.performRequest(request);
			String responseBody = EntityUtils.toString(response.getEntity());
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(responseBody);
			JsonObject count = element.getAsJsonObject();
			JsonObject count1 = (JsonObject)count.get("aggregations");
			JsonObject count2 = (JsonObject)count1.get("group_by_state");
			JsonArray count3 = (JsonArray)count2.get("buckets");
			for(int i=0; i<count3.size(); i++) {
				Map<String, Object> result = new HashMap<>();
				JsonObject fa = (JsonObject) count3.get(i);
				result.put("Controller", fa.get("key").getAsString());
				JsonObject fb = (JsonObject)fa.get("group_by_state");
				JsonArray fc = (JsonArray)fb.get("buckets");
				System.out.println("fc..."+fc.toString());
				for(int j=0; j<fc.size(); j++) {
					JsonObject fd = (JsonObject)fc.get(j);
					result.put(fd.get("key").getAsString(), fd.get("doc_count").getAsInt());
					
					System.out.println("fd..."+fd.toString());
					
				}
				
				list.add(result);

			}
	
		}
		 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return list;
	}
	
	
	public List<Map<String, Object>> ssElasticsearchExec(Request request) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();


		try {
			Response response = client.performRequest(request);
			String responseBody = EntityUtils.toString(response.getEntity());
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(responseBody);
			JsonObject count = element.getAsJsonObject();
			JsonObject count1 = (JsonObject)count.get("aggregations");
			JsonObject count2 = (JsonObject)count1.get("group_by_state");
			JsonArray count3 = (JsonArray)count2.get("buckets");

			for(int i=0; i<count3.size(); i++) {
					Map<String, Object> result = new HashMap<>();
					JsonObject fa = (JsonObject)count3.get(i);
					result.put("Controller", fa.get("key_as_string").getAsString()+"_flag");
					result.put("Flag", fa.get("key").getAsString());
					result.put("Count", fa.get("doc_count").getAsInt());	
					list.add(result);
			}	
		}
		 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return list;
	}
	
	public List<Map<String, Object>> ssFailElasticsearchExec(Request request) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();


		try {
			Response response = client.performRequest(request);
			String responseBody = EntityUtils.toString(response.getEntity());
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(responseBody);
			JsonObject count = element.getAsJsonObject();
			JsonObject count1 = (JsonObject)count.get("aggregations");
			JsonObject count2 = (JsonObject)count1.get("group_by_state");
			JsonArray count3 = (JsonArray)count2.get("buckets");

			for(int i=0; i<count3.size(); i++) {
					Map<String, Object> result = new HashMap<>();
					JsonObject fa = (JsonObject)count3.get(i);
					result.put("Controller", fa.get("key").getAsString());
					result.put("Flag", fa.get("key").getAsString());
					result.put("Count", fa.get("doc_count").getAsInt());
					list.add(result);
			}	
		}
		 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return list;
	}
	
	
	
	
}
	