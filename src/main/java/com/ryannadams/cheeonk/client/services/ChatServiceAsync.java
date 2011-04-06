package com.ryannadams.cheeonk.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.client.IBuddy;
import com.ryannadams.cheeonk.client.IChat;
import com.ryannadams.cheeonk.client.IMessage;

public interface ChatServiceAsync
{
	void login(String username, String password, AsyncCallback<Boolean> callback);

	void logout(AsyncCallback<Boolean> callback);

	void getBuddyList(AsyncCallback<IBuddy[]> callback);

	void addBuddy(IBuddy buddy, AsyncCallback<Void> callback);

	void removeBuddy(IBuddy buddy, AsyncCallback<Void> callback);

	void createChat(String username, AsyncCallback<Void> callback);

	void sendMessage(IChat key, String message, AsyncCallback<Void> callback);

	void getMessages(IChat key, AsyncCallback<IMessage[]> callback);
}
