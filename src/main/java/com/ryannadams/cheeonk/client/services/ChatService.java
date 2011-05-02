package com.ryannadams.cheeonk.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ryannadams.cheeonk.shared.ChatServerKey;
import com.ryannadams.cheeonk.shared.buddy.ClientBuddy;
import com.ryannadams.cheeonk.shared.chat.ClientChat;
import com.ryannadams.cheeonk.shared.message.ClientMessage;

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

	ClientChat createChat(ChatServerKey connectionKey, String recipient);

	ClientChat[] getIncomingChats(ChatServerKey connectionKey);

	void sendMessage(ChatServerKey connectionKey, ClientChat chat, String message);

	ClientMessage[] getMessages(ChatServerKey connectionKey, ClientChat chat);

	Boolean register(String username, String password);
}
