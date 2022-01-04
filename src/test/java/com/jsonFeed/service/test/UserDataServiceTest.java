package com.jsonFeed.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsonFeed.dto.JsonResponseDTO;
import com.jsonFeed.entities.UserData;
import com.jsonFeed.serviceImpl.UserJsonFeedServiceImpl;

@RunWith(SpringRunner.class)
public class UserDataServiceTest {
	
	
	@InjectMocks
	UserJsonFeedServiceImpl userJsonFeedServiceImpl;
	
//	Get Modified UserData -- Success Test
	@Test
	public void retriveAllTheUserFromJSON_SuccessTest() {
		
		try {
			ObjectMapper mapper = new ObjectMapper();

			File configFile = new File(getClass().getResource("/static/userData_1.json").getFile());
			List<UserData> userList = mapper.readValue(configFile, new TypeReference<List<UserData>>() {
			});
			when(userJsonFeedServiceImpl.retriveAllUserData_FromJSON()).thenReturn(userList);
			
			JsonResponseDTO respObj = new JsonResponseDTO();
			respObj.setNoOfEntries(userList.size());
			int uniqueUser = userJsonFeedServiceImpl.retriveAllUniqueUserID_FromJSON(userList);
			respObj.setNoOfUniqueUsers(uniqueUser);
			respObj.setUserList(userList);
			final List<UserData> response = (List<UserData>) userJsonFeedServiceImpl.getFinalModifiedJson(2, userList);
			assertNotNull(response);

			assertEquals(respObj.getNoOfEntries(),  ((JsonResponseDTO) response).getNoOfEntries());
			assertEquals(respObj.getNoOfUniqueUsers(), ((JsonResponseDTO) response).getNoOfUniqueUsers());
			assertEquals(respObj.getUserList(), ((JsonResponseDTO) response).getUserList());
		} catch (Exception e) {

		}
		
	}
	
	// When Json Data is not present.
	@Test
	public void retrieveModifiedUserDataTestWhenJsonDataNotAvailable_Test() {
		try {
			List<UserData> listOfUserData = new ArrayList<>();
			when(userJsonFeedServiceImpl.retriveAllUserData_FromJSON()).thenReturn(listOfUserData);
			final List<UserData> response = (List<UserData>) userJsonFeedServiceImpl.getFinalModifiedJson(3, listOfUserData);
			assertNull(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	When User Trying to Modify the Data with Empty List
	@Test
	public void modifyJsonTestWhenListOfUserDataIsEmpty_Test() {
		try {
			List<UserData> listOfUserData = new ArrayList<>();
			
			final List<UserData> response = (List<UserData>) userJsonFeedServiceImpl.getFinalModifiedJson(4, listOfUserData);

			assertNull(response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// When Index is Negative While Retriving Data.
	@Test
	public void modifyJsonTestWhenListLocationToModifyIsNegative_Test() {

		try {
			ObjectMapper mapper = new ObjectMapper();
			File configFile = new File(getClass().getResource("/static/userData_1.json").getFile());
			
			List<UserData> listOfUserData = mapper.readValue(configFile, new TypeReference<List<UserData>>() {});
			
			final List<UserData> response = (List<UserData>) userJsonFeedServiceImpl.getFinalModifiedJson(-1, listOfUserData);
			
			assertNull(response);
		} catch (Exception e) {

		}

	}
	
	// userlist is empty
	@Test
	public void tallyNoOfUsersTestWhenListIsEmpty_Test() {

		try {
			List<UserData> listOfUserData = new ArrayList<>();

			final Integer noOfUniqueUsers = (int) userJsonFeedServiceImpl.retriveNumberOfUserCount_FromJSON(listOfUserData);
			
			assertThat(noOfUniqueUsers).isZero();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
