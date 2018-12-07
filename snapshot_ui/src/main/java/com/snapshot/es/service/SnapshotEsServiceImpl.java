package com.snapshot.es.service;

import java.io.IOException;
import java.util.Map;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
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
	public int getGwTotalCount(SnapshotEsVO reqfg) {
		
		String url = "gw_log-" + reqfg.getDate() + "/_count";
		String querydsl = "{ \"query\" : " 													+ 
						   "{ \"bool\" : "													+
							"{ \"must\" : [ " 												+
							 "{ \"match\" : { \"type\" : \"" + reqfg.getController() + "\" } } , "    +
							  "{ \"range\" : { " 											+
							   " \"reqdate\" : { "  										+
							        " \"gte\" : \"" + reqfg.getFromdt() + "\", " 				+
							        " \"lte\" : \"" + reqfg.getTodt()  + "\", "  				+
							        " \"format\" : \"yyyy-MM-dd\" " 						+
							      "}}}],"													+
							       "\"must_not\" : { "										+
							       "	\"match\" : \"" + reqfg.getType() + "\" "		+
							       "}}}}";
		System.out.println(querydsl);
		Request request = new Request("GET",url);	
		request.setJsonEntity(querydsl);			
		System.out.println("getTotlaCount임!!!!!");
		return elasticsearchExec(request);
	}
	
	
	@Override
	public int getGwTotalSuccessCount(SnapshotEsVO reqfg) {
		String url = "gw_log-" + reqfg.getDate() + "/_count";
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
							      "}}}]}}}";
		System.out.println(querydsl);
		Request request = new Request("GET",url);	
		request.setJsonEntity(querydsl);			
		System.out.println("getTotalSuccessCount임!!!!!");
		return elasticsearchExec(request);
	}
	@Override
	public Map getGwTotalFailCount(SnapshotEsVO reqfg) {
		String url = "gw_log-" + reqfg.getDate() + "/_count";
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
							      "}}}]}}}";
		System.out.println(querydsl);
		Request request = new Request("GET",url);	
		request.setJsonEntity(querydsl);			
		System.out.println("getTotalFailCount임!!!!!");
		return elasticsearchExec(request);
	}
	@Override
	public Map getFailCaseCount(SnapshotEsVO reqfg) {
		// TODO Auto-generated method stub
		return null;
	}

	

	


	
	public int elasticsearchExec(Request request) {
		int count =0;
		try {
			Response response = client.performRequest(request);
			String responseBody = EntityUtils.toString(response.getEntity());
			System.out.println("responseBody!!!!!!~"+responseBody);
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(responseBody);
			count = element.getAsJsonObject().get("count").getAsInt();
			System.out.println("결과다"+count);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return count;
	}
}
