package com.jsonFeed.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.jsonFeed.entities.UserData;

public interface JsonFeedService {
	
	
	public List<UserData> retriveAllUserData_FromJSON ();

	public long retriveNumberOfUserCount_FromJSON (List<UserData> userData);
		
	public Integer retriveAllUniqueUserID_FromJSON (List<UserData> userData);

	public ResponseEntity<List<UserData>> updateUserTitle_AndBodyText_FromJSON ();
	
	public List<UserData> getFinalModifiedJson(int index,List<UserData> userData);
}
