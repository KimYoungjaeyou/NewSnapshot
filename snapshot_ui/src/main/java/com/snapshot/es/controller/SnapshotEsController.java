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

			   elasticApi.getGwTotalCount(new SnapshotEsVO("2018-12","","called_api","2018-12-04","2018-12-05",""));

	   }
	   
	   @RequestMapping("/snapshot")
	   public void getSnapshotReqCount() {
		   ArrayList<String> elasticController = new ArrayList<>();
		   
 		   		elasticApi.getSsCount(new SnapshotEsVO("2018-12","snapshotSelectController","result_obj","20181201","20181204",""));
 		   		elasticApi.getSsFailCount(new SnapshotEsVO("2018-12","snapshotSelectController","result_obj","20181201","20181204","false"));
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
