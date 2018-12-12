package com.snapshot.es.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	   public String getGatewayReqCount(Model model) {
			
		   List<Map<String, Object>> list =  elasticApi.getGwTotalCount("2018-12","2018-12-04","2018-12-05");
				for (Map<String, Object> map : list) {
 					System.out.println(map.toString());
					model.addAttribute((String)map.get("Controller"),map);
 				    for (Map.Entry<String, Object> entry : map.entrySet()) {
 				        String key = entry.getKey();
 				        Object value = entry.getValue();
 				        System.out.println(" 컨트롤러임 ! key!!!+ : " + key + "   value !!!+ : " + value );
 				    }
 				}
				  System.out.println("--- Model data ---");
				  Map<String, Object> modelMap = model.asMap();
				  for (Object modelKey : modelMap.keySet()) {
				    Object modelValue = modelMap.get(modelKey);
				    System.out.println(modelKey + " -- " + modelValue);
				  }
				
			return "index";	
				
				
	   }
	   
	   @RequestMapping("/snapshot")
	   public String getSnapshotReqCount(Model model) {
		   ArrayList<String> elasticController = new ArrayList<>();
		   List<Map<String, Object>> list = elasticApi.getSsCount("2018-12","2018-12-04","2018-12-05");
 				for (Map<String, Object> map : list) {
 					System.out.println(map.toString());
 					System.out.println("?????"+(String)map.get("Controller"));
 					model.addAttribute((String)map.get("Controller"),map);
 				    for (Map.Entry<String, Object> entry : map.entrySet()) {
 				        String key = entry.getKey();
 				        Object value = entry.getValue();
 				        System.out.println(" 컨트롤러임 ! key!!!+ : " + key + "   value !!!+ : " + value );
 				    }
 				}
 		 List<Map<String, Object>> list2 = elasticApi.getSsFailCount("2018-12","2018-12-04","2018-12-05");
 		model.addAttribute("FailReason",list2);
			for (Map<String, Object> map : list2) {
				System.out.println((String)map.get("Controller"));
					System.out.println(map.toString());
					model.addAttribute((String)map.get("Controller"),map);
				    for (Map.Entry<String, Object> entry : map.entrySet()) {
				        String key = entry.getKey();
				        Object value = entry.getValue();
				        System.out.println(" 컨트롤러임 ! key!!!+ : " + key + "   value !!!+ : " + value );
				    }
				}
			  System.out.println("--- Model data ---");
			  Map<String, Object> modelMap = model.asMap();
			  for (Object modelKey : modelMap.keySet()) {
			    Object modelValue = modelMap.get(modelKey);
			    System.out.println(modelKey + " -- " + modelValue);
			  }
			return "index";
	   }

}
