package com.ryannadams.cheeonk.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ryannadams.cheeonk.client.chat.ClientBuddy;
import com.ryannadams.cheeonk.client.chat.ClientChat;
import com.ryannadams.cheeonk.client.chat.ClientMessage;

@RemoteServiceRelativePath("chat")
public interface ChatService extends RemoteService
{
	Boolean login(String username, String password);

	Boolean logout();

	ClientBuddy[] getBuddyList();

	void addBuddy(ClientBuddy buddy);

	void removeBuddy(ClientBuddy buddy);

	ClientBuddy[] pollBuddyUpdates();

	ClientChat createChat(String recipient);

	ClientChat[] pollIncomingChats();

	void sendMessage(ClientChat key, String message);

	ClientMessage[] getMessages(ClientChat key);

	ClientMessage[] pollIncomingMessages(ClientChat key);

}
