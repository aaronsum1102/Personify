package com.Personify.integration;

import java.util.*;

public class Messenger {
	private List<String> systemMessages;

	public Messenger() {
		systemMessages = new ArrayList<>();
	}

	public boolean addMessage(String message) {
		if (message.length() > 0) {
			return systemMessages.add(message);
		}
		return false;
	}
	
	public void clearMessages() {
		systemMessages.clear();
	}
	
	public List<String> getMessages() {
		return systemMessages;
	}

}
