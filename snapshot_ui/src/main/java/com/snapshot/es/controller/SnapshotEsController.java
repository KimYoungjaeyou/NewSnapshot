package com.snapshot.es.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.snapshot.es.service.SnapshotEsService;

@Controller
public class SnapshotEsController {

	@Autowired
	SnapshotEsService elasticApi;
	
	private final String ELASTIC_INDEX = "reviews-sample";
	
	
	   @RequestMapping("/")
	   public String index() {
			System.out.println("test");
	      return "index";
	   }
	   
	   @RequestMapping("/test")
	   public void getcount() {
			String url = ELASTIC_INDEX + "/_count?q=username:*";
			System.out.println("test");
			Map<String, Object> result = elasticApi.callElasticApi("GET", url, null, null);
			System.out.println(result.get("resultCode"));
			System.out.println(result.get("resultBody"));
	   }
//
//	   @PostMapping("/index")
//	   public String sayHello(@RequestParam("name") String name, Model model) {
//	      model.addAttribute("name", name);
//	      return "hello";
//	   }
}
