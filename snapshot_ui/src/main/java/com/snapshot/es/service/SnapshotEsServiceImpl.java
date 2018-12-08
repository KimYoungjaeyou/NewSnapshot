package com.snapshot.es.service;

import java.io.IOException;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public int getGwTotalCount(SnapshotEsVO reqfg) {
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
		return elasticsearchExec(request);
	}
	
	@Override
	public int getSsCount(SnapshotEsVO reqfg) {
		
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
							      "}}}]}}} ,"			+
							      " \"size\" : 0 ," 		+
							     "\"aggs\" : { " +
							     "\"group_by_state\" : { " +
							     "\"terms\" : { " +
							     "\"field\" : \"success\" "+
							     "}}}";
		
		System.out.println(querydsl);
		Request request = new Request("GET",url);	
		request.setJsonEntity(querydsl);			
		System.out.println("getSsCount임!!!!!");
		return elasticsearchExec(request);
	}

	@Override
	public int getSsFailCount(SnapshotEsVO reqfg) {
		
		String url = "gw_log-" + reqfg.getDate() + "/_search";
		String querydsl = "{ \"query\" : " 													+ 
						   "{ \"bool\" : "													+
							"{ \"must\" : [ " 												+
							 "{ \"match\" : { \"Controller\" : \"" + reqfg.getController() + "\" } } , "    +
							  "{ \"match\" : { \"type\" : \"" + reqfg.getType() + "\" } } , " +
							  "{ \"match\" : { \"success\" : \"" + reqfg.getSuccess() + "\" } }  " +
							  "{ \"range\" : { " 											+
							   " \"reqdate\" : { "  										+
							        " \"gte\" : \"" + reqfg.getFromdt() + "\", " 				+
							        " \"lte\" : \"" + reqfg.getTodt()  + "\", "  				+
							        " \"format\" : \"yyyy-MM-dd\" " 						+
							      "}}}]}}} ,"			+
							      " \"size\" : 0 ," 		+
							     "\"aggs\" : { " +
							     "\"group_by_state\" : { " +
							     "\"terms\" : { " +
							     "\"field\" : \"failReason.keyword\" "+
							     "}}}";
		
		System.out.println(querydsl);
		Request request = new Request("GET",url);	
		request.setJsonEntity(querydsl);			
		System.out.println("getSsFailCount임!!!!!");
		// TODO Auto-generated method stub
		return elasticsearchExec(request);
	}

	


	
	public int elasticsearchExec(Request request) {
		JsonArray count;
		try {
			Response response = client.performRequest(request);
			String responseBody = EntityUtils.toString(response.getEntity());
			System.out.println("responseBody!!!!!!~"+responseBody);
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(responseBody);
			System.out.println("element##" + element);
			count = (JsonArray) element.getAsJsonObject().get("aggregations");
			JsonArray jArray =  count.getAsJsonArray(); 
			System.out.println("결과다"+jArray.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return 0;
	}
}
