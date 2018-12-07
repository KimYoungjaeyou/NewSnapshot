package com.snapshot.es.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.snapshot.es.service.SnapshotEsService;
import com.snapshot.es.vo.SnapshotEsVO;

@Controller
public class SnapshotEsController {
	

	@Autowired
	SnapshotEsService elasticApi;
	
	   @RequestMapping("/")
	   public String index(){
	      return "index";
	   }
		
		
	   @RequestMapping("/gateway")
	   public void getGatewayReqCount() {
		   ArrayList<String> elasticController = new ArrayList<>();
 		   elasticController.add("snapshotcreateController");
 		   elasticController.add("snapshotSelectController");
 		   elasticController.add("snapshotDeleteController");
		   for(int i=0; i<elasticController.size(); i++) {
			   elasticApi.getGwTotalCount(new SnapshotEsVO("2018-12",elasticController.get(i),"called_api","2018-12-04","2018-12-05","true false","tt"));
			   elasticApi.getGwTotalSuccessCount(new SnapshotEsVO("20181211",elasticController.get(i),"create_obj result_obj delete_obj","20181201","20181204","true","tt"));
			   elasticApi.getGwTotalFailCount(new SnapshotEsVO("20181211",elasticController.get(i),"error_log","20181201","20181204","true","tt"));
		   }
	   }
	   
	   @RequestMapping("/snapshot")
	   public void getSnapshotReqCount() {
		   ArrayList<String> elasticController = new ArrayList<>();
 		   elasticController.add("snapshotcreateController");
 		   elasticController.add("snapshotSelectController");
 		   elasticController.add("snapshotDeleteController");
		   for(int i=0; i<elasticController.size(); i++) {
			   elasticApi.getTotalcount(new SnapshotEsVO("2018-12",elasticController.get(i),"called_api","2018-12-04","2018-12-05","true false","tt"));
			   elasticApi.getTotalSuccessCount(new SnapshotEsVO("20181211",elasticController.get(i),"create_obj result_obj delete_obj","20181201","20181204","true","tt"));
			   elasticApi.getTotalFailCount(new SnapshotEsVO("20181211",elasticController.get(i),"error_log","20181201","20181204","true","tt"));
		   }
	   }
	   
//	   @RequestMapping("/")
//	   public void getcount() {
//			String url = ELASTIC_INDEX + "/_count";
//			System.out.println("test");
//			Map<String, Object> result = elasticApi.callElasticApi("GET", url,querydsl);
////			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//
//	
//			
//			try {
//				String json = ow.writeValueAsString(result.get("resultBody"));
//				JsonParser parser = new JsonParser();;
//				JsonObject element = (JsonObject) parser.parse(json);
//				int cnt = element.getAsJsonObject().get("count").getAsInt();
//				System.out.println("CNT다..###"+cnt);
//				System.out.println("json#####"+json);
//			} catch (JsonProcessingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			System.out.println(result.get("resultBody"));
			
	   
//
//	   @PostMapping("/index")
//	   public String sayHello(@RequestParam("name") String name, Model model) {
//	      model.addAttribute("name", name);
//	      return "hello";
//	   }
}
