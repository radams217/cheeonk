package com.ryannadams.cheeonk.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ryannadams.cheeonk.client.chat.ClientBuddy;
import com.ryannadams.cheeonk.client.chat.ClientChat;
import com.ryannadams.cheeonk.client.chat.ClientMessage;
import com.ryannadams.cheeonk.shared.chat.ConnectionKey;

@RemoteServiceRelativePath("chat")
public interface ChatService extends RemoteService
{
	String login(ConnectionKey connectionKey);

	Boolean logout(ConnectionKey connectionKey);

	ClientBuddy[] getBuddyList(ConnectionKey connectionKey);

	void addBuddy(ConnectionKey connectionKey, ClientBuddy buddy);

	void removeBuddy(ConnectionKey connectionKey, ClientBuddy buddy);

	ClientBuddy[] getBuddyUpdates(ConnectionKey connectionKey);

	ClientChat createChat(ConnectionKey connectionKey, String recipient);

	ClientChat[] getIncomingChats(ConnectionKey connectionKey);

	void sendMessage(ConnectionKey connectionKey, ClientChat chat,
			String message);

	ClientMessage[] getMessages(ConnectionKey connectionKey, ClientChat chat);

}
