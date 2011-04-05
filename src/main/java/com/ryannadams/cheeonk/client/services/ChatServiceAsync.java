package com.ryannadams.cheeonk.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChatServiceAsync
{
	void login(String username, String password, AsyncCallback<Boolean> callback);

	void logout(AsyncCallback<Boolean> callback);

	void createChat(String username, AsyncCallback<Void> callback);

	void getBuddyList(AsyncCallback<List<String>> callback);

	void sendMessage(String message, AsyncCallback<Void> callback);

	void getMessages(AsyncCallback<String[]> callback);
}
