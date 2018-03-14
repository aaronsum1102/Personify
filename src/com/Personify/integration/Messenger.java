package com.Personify.integration;

import java.util.*;

public class Messages {
	private List<String> systemMessages;

	public Messages() {
		systemMessages = new ArrayList<>();
	}

	public boolean addMessage(String message) {
		return systemMessages.add(message);
	}
	
	public void clearMessages() {
		systemMessages.clear();
	}
	
	public List<String> getMessages() {
		return systemMessages;
	}

}
