package com.jsonFeed.dto;

import java.util.List;

import com.jsonFeed.entities.UserData;


public class JsonResponseDTO {

	
	long noOfEntries;

	Integer noOfUniqueUsers;
	
	List<UserData> userList;


	public long getNoOfEntries() {
		return noOfEntries;
	}

	public void setNoOfEntries(long noOfEntries) {
		this.noOfEntries = noOfEntries;
	}

	public Integer getNoOfUniqueUsers() {
		return noOfUniqueUsers;
	}

	public void setNoOfUniqueUsers(Integer noOfUniqueUsers) {
		this.noOfUniqueUsers = noOfUniqueUsers;
	}

	public List<UserData> getUserList() {
		return userList;
	}

	public void setUserList(List<UserData> userList) {
		this.userList = userList;
	}

	@Override
	public String toString() {
		return "JsonResponseDTO [noOfEntries=" + noOfEntries + ", noOfUniqueUsers=" + noOfUniqueUsers + ", userList="
				+ userList + "]";
	}


	
}
