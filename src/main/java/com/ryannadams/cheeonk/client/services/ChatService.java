package com.ryannadams.cheeonk.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("chat")
public interface ChatService extends RemoteService
{
	Boolean login(String username, String password);

	Boolean logout();

	void createChat(String recipient);

	List<String> getBuddyList();

	void sendMessage(String message);

	String[] getMessages();
}
