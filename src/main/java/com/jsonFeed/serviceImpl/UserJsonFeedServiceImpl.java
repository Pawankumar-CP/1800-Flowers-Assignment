package com.jsonFeed.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.jsonFeed.entities.UserData;
import com.jsonFeed.service.JsonFeedService;

@Service
public class UserJsonFeedServiceImpl implements JsonFeedService {

	@Autowired
	RestTemplate restTemplate;
	
	@Value(value = "${json.data.url}")
	String url;
	
	public static final String REPLACEMENT_TEXT = "1800Flowers";
	
//	Retrieve All the Raw Json Data.
	@Override
	public List<UserData> retriveAllUserData_FromJSON () {
		try {
		ResponseEntity<List<UserData>> retriveListOfUserFromJSON = restTemplate.exchange(url,
				HttpMethod.GET, null, new ParameterizedTypeReference<List<UserData>>() {
				});
		
		return retriveListOfUserFromJSON.getBody();
		}catch(RestClientException  e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
//	Retrieve Total Number OF User Present in Raw Json.
	@Override
	public long retriveNumberOfUserCount_FromJSON (List<UserData> userData) {
		
		return userData.stream().count();
	
	}
	
//	Retrieve The Unique User Count From the Json(With No Repeated UserId)
	@Override
	public Integer retriveAllUniqueUserID_FromJSON (List<UserData> userData) {
		 try {
			 
				List<Integer> unCount = userData.stream()
						.map(getUniqueUserTotalCount -> getUniqueUserTotalCount.getUserId())
						.distinct()
						.collect(Collectors.toList());
				
				return  unCount.size();
		 
		 }catch(Exception e) {
			  e.printStackTrace();
		 }
		 
		 return null;
	}
	
//	Update User 'Title & Body' Text Content Message From the Json By Passing Index.
	@Override
	public ResponseEntity<List<UserData>> updateUserTitle_AndBodyText_FromJSON () {
		try {
			
			List<UserData> userData = retriveAllUserData_FromJSON();
			
			 List<UserData> getUser = userData.stream()
			  .filter(u -> u.getId() == 4)
			  .collect(Collectors.toList());
			 
			 for(UserData u : getUser) {
					String bodyContent = u.getBody();
					String titleContent = u.getTitle();
					String replaceTitle = titleContent.replace(titleContent,REPLACEMENT_TEXT);
					String replaceBody = bodyContent.replace(bodyContent,REPLACEMENT_TEXT);
					u.setTitle(replaceTitle);
					u.setBody(replaceBody);
			 
			 }
			 
			 return new ResponseEntity<List<UserData>>(getUser, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return (ResponseEntity<List<UserData>>) ResponseEntity.EMPTY;
		
	}
	
//	Get The Final Modified Json Report Along With All the 'UserCount/QuniueUserCount/Replaced Text as Single Json.
	@Override
	public List<UserData> getFinalModifiedJson(int index,List<UserData> userData) {
		try {
		List<UserData> finalUser = new ArrayList<UserData>(userData.size());
			String bodyContent = userData.get(index).getBody();
			String titleContent = userData.get(index).getTitle();
			
		
			String replaceTitle = titleContent.replace(titleContent, REPLACEMENT_TEXT);
			String replaceBody = bodyContent.replace(bodyContent, REPLACEMENT_TEXT);
			
			userData.get(index).setBody(replaceBody);
			userData.get(index).setTitle(replaceTitle);
			
		
		for(UserData i : userData) {
			finalUser.add(i);
		}
		return finalUser;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
