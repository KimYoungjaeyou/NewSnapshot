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

 
//    /**
//     * 엘라스틱서치에서 제공하는 api를 이용한 전송메소드
//     * @param method
//     * @param url
//     * @param obj
//     * @param jsonData
//     * @return
//     */
//    public Map<String, Object> callElasticApi(String method, String url, String querydsl) {
//        Map<String, Object> result = new HashMap<>();
//        //엘라스틱서치에서 제공하는 restClient를 통해 엘라스틱서치에 접속한다
//        System.out.println("querydsl###" + querydsl);
//        Request request = new Request(method,url);
//        request.setJsonEntity(querydsl);
//		try {
//			Response response = client.performRequest(request);
//			String responseBody = EntityUtils.toString(response.getEntity());
//			System.out.println("responseBody!!!!!!~"+responseBody);
//			JsonParser parser = new JsonParser();
//			JsonElement element = parser.parse(responseBody);
//			String count = element.getAsJsonObject().get("count").getAsString();
//			System.out.println("결과다"+count);
//		} catch (IOException e) {
//			// 
//			e.printStackTrace();
//		}
//
//        
////        try(RestClient restClient = RestClient.builder(new HttpHost(host, port)).build()) {
////            Map<String, String> params =  Collections.singletonMap("pretty", "true");
////            //엘라스틱서치에서 제공하는 response 객체
////            Response response = null;
////
////            //GET, DELETE 메소드는 HttpEntity가 필요없다
////            if (method.equals("GET") || method.equals("DELETE")) {
////                response = restClient.performRequest(method, url, params);
////            } 
////            //앨라스틱서치에서 리턴되는 응답코드를 받는다
////            int statusCode = response.getStatusLine().getStatusCode();
////            //엘라스틱서치에서 리턴되는 응답메시지를 받는다
////            String responseBody = EntityUtils.toString(response.getEntity());
////            result.put("resultCode", statusCode);
////            result.put("resultBody", responseBody);
////        } catch (Exception e) {
////            result.put("resultCode", -1);
////            result.put("resultBody", e.toString());
////        }
//        return result;
    
	@Autowired
	private RestClient client;
	
    @Autowired
    public SnapshotEsServiceImpl(RestClient client) {
        this.client = client;
    }	
    

	
	@Override
	public List<Map<String, Object>> getGwTotalCount(SnapshotEsVO reqfg) {
		String url = "gw_log-" + reqfg.getDate() + "/_search";
		String querydsl = "{ \"query\" : " 													+ 
						   "{ \"bool\" : "													+
							"{ \"must\" : [ " 												+
							  "{ \"range\" : { " 											+
							   " \"reqdate\" : { "  										+
							        " \"gte\" : \"" + reqfg.getFromdt() + "\", " 				+
							        " \"lte\" : \"" + reqfg.getTodt()  + "\", "  				+
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
	public List<Map<String, Object>> getSsCount(SnapshotEsVO reqfg) {
		
		String url = "gw_log-" + reqfg.getDate() + "/_search";
		String querydsl = "{ \"query\" : " 													+ 
						   "{ \"bool\" : "													+
							"{ \"must\" : [ " 												+
							 "{ \"match\" : { \"Controller\" : \"" + reqfg.getController() + "\" } } , "    +
							  "{ \"match\" : { \"type\" : \"" + reqfg.getType() + "\" } } , " +
							  "{ \"range\" : { " 											+
							   " \"reqdate\" : { "  										+
							        " \"gte\" : \"" + reqfg.getFromdt() + "\", " 				+
							        " \"lte\" : \"" + reqfg.getTodt()  + "\", "  				+
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
	public List<Map<String, Object>> getSsFailCount(SnapshotEsVO reqfg) {
		
		String url = "gw_log-" + reqfg.getDate() + "/_search";
		String querydsl = "{ \"query\" : " 													+ 
						   "{ \"bool\" : "													+
							"{ \"must\" : [ " 												+
							 "{ \"match\" : { \"Controller\" : \"" + reqfg.getController() + "\" } } , "    +
							  "{ \"match\" : { \"type\" : \"" + reqfg.getType() + "\" } } , " +
							  "{ \"match\" : { \"success\" : \"" + reqfg.getSuccess() + "\" } } , " +
							  "{ \"range\" : { " 											+
							   " \"reqdate\" : { "  										+
							        " \"gte\" : \"" + reqfg.getFromdt() + "\", " 				+
							        " \"lte\" : \"" + reqfg.getTodt()  + "\", "  				+
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
		return ssElasticsearchExec(request);
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
					result.put(fd.get("key").getAsString(), fd.get("doc_count").getAsString());	
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
					result.put(fa.get("key").getAsString(), fa.get("doc_count").getAsString());	
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
	