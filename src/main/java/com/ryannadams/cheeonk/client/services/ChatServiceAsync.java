package com.ryannadams.cheeonk.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.client.chat.ClientBuddy;
import com.ryannadams.cheeonk.client.chat.ClientChat;
import com.ryannadams.cheeonk.client.chat.ClientMessage;

public interface ChatServiceAsync
{
	void login(String username, String password, AsyncCallback<Boolean> callback);

	void logout(AsyncCallback<Boolean> callback);

	void getBuddyList(AsyncCallback<ClientBuddy[]> callback);

	void addBuddy(ClientBuddy buddy, AsyncCallback<Void> callback);

	void removeBuddy(ClientBuddy buddy, AsyncCallback<Void> callback);

	void pollBuddyUpdates(AsyncCallback<ClientBuddy[]> callback);

	void createChat(String username, AsyncCallback<ClientChat> callback);

	void pollIncomingChats(AsyncCallback<ClientChat[]> callback);

	void sendMessage(ClientChat key, String message,
			AsyncCallback<Void> callback);

	void getMessages(ClientChat key, AsyncCallback<ClientMessage[]> callback);

	void pollIncomingMessages(ClientChat key,
			AsyncCallback<ClientMessage[]> callback);
}
