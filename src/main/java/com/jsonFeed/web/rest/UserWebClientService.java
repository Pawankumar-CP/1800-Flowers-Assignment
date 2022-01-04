package com.jsonFeed.web.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jsonFeed.dto.JsonResponseDTO;
import com.jsonFeed.entities.UserData;
import com.jsonFeed.service.JsonFeedService;
import com.jsonFeed.service.KafkaProducer;


@RestController
@RequestMapping("api/jsonFeed")
@CrossOrigin(origins = {"http://localhost"})
public class UserWebClientService {
	
	@Autowired
	KafkaProducer kafkaProducer;
	
	@Autowired
	JsonFeedService jsonFeedService;

	
	@GetMapping(value = "/getFinalList")
	public JsonResponseDTO finalList(@RequestParam(value = "index") int index) throws Exception {
		
		List<UserData> userData = jsonFeedService.retriveAllUserData_FromJSON();
		long totalCount =   jsonFeedService.retriveNumberOfUserCount_FromJSON(userData);
		List<UserData> getUserByIndex = jsonFeedService.getFinalModifiedJson(index,userData);
		Integer noOfUniqueUser = jsonFeedService.retriveAllUniqueUserID_FromJSON(userData);
		
		JsonResponseDTO jsonResponse = new JsonResponseDTO();
		jsonResponse.setNoOfEntries(totalCount);
		jsonResponse.setNoOfUniqueUsers(noOfUniqueUser);
		jsonResponse.setUserList(getUserByIndex);
		
		 ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    	 String json = null;
    	 json = objectWriter.writeValueAsString(jsonResponse.getUserList());
		 kafkaProducer.sendMessage(json);
		
		 return jsonResponse;
	}
	
}
