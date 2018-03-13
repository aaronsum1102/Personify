package com.Personify.integration;

import java.util.*;

public class Messages {
	private List<String> taskMessages;

	public Messages() {
		taskMessages = new ArrayList<>();
	}

	public boolean addMessage(String message) {
		return taskMessages.add(message);
	}
	
	public void clearMessages() {
		taskMessages.clear();
	}
	
	public List<String> getMessages() {
		return taskMessages;
	}

}
