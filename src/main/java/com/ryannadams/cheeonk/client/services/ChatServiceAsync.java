package com.ryannadams.cheeonk.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.client.chat.ClientBuddy;
import com.ryannadams.cheeonk.client.chat.ClientChat;
import com.ryannadams.cheeonk.client.chat.ClientMessage;
import com.ryannadams.cheeonk.shared.chat.ConnectionKey;

public interface ChatServiceAsync
{
	void login(ConnectionKey connectionKey, AsyncCallback<String> callback);

	void logout(ConnectionKey connectionKey, AsyncCallback<Boolean> callback);

	void getBuddyList(ConnectionKey connectionKey,
			AsyncCallback<ClientBuddy[]> callback);

	void addBuddy(ConnectionKey connectionKey, ClientBuddy buddy,
			AsyncCallback<Void> callback);

	void removeBuddy(ConnectionKey connectionKey, ClientBuddy buddy,
			AsyncCallback<Void> callback);

	void getBuddyUpdates(ConnectionKey connectionKey,
			AsyncCallback<ClientBuddy[]> callback);

	void createChat(ConnectionKey connectionKey, String username,
			AsyncCallback<ClientChat> callback);

	void getIncomingChats(ConnectionKey connectionKey,
			AsyncCallback<ClientChat[]> callback);

	void sendMessage(ConnectionKey connectionKey, ClientChat chat,
			String message, AsyncCallback<Void> callback);

	void getMessages(ConnectionKey connectionKey, ClientChat chat,
			AsyncCallback<ClientMessage[]> callback);
}
