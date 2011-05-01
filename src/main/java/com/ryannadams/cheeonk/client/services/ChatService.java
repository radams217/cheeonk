package com.ryannadams.cheeonk.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ryannadams.cheeonk.client.buddy.ClientBuddy;
import com.ryannadams.cheeonk.client.chat.ClientChat;
import com.ryannadams.cheeonk.client.message.ClientMessage;
import com.ryannadams.cheeonk.shared.chat.ChatServerKey;

/**
 * @author radams217 This interface specifies the Contract for a ChatService
 */
@RemoteServiceRelativePath("chat")
public interface ChatService extends RemoteService
{
	String login(ChatServerKey connectionKey);

	Boolean logout(ChatServerKey connectionKey);

	ClientBuddy[] getBuddyList(ChatServerKey connectionKey);

	void addBuddy(ChatServerKey connectionKey, ClientBuddy buddy);

	void removeBuddy(ChatServerKey connectionKey, ClientBuddy buddy);

	ClientBuddy[] getBuddyUpdates(ChatServerKey connectionKey);

	ClientChat createChat(ChatServerKey connectionKey, String recipient);

	ClientChat[] getIncomingChats(ChatServerKey connectionKey);

	void sendMessage(ChatServerKey connectionKey, ClientChat chat, String message);

	ClientMessage[] getMessages(ChatServerKey connectionKey, ClientChat chat);

	Boolean register(String username, String password);
}
