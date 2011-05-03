package com.ryannadams.cheeonk.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.shared.ChatServerKey;
import com.ryannadams.cheeonk.shared.buddy.ClientBuddy;
import com.ryannadams.cheeonk.shared.chat.ClientChat;
import com.ryannadams.cheeonk.shared.message.ClientMessage;

/**
 * @author radams217
 * 
 */
public interface ChatServiceAsync
{
	/**
	 * @param connectionKey
	 * @param callback
	 * 
	 *            Login to the XMPPServer using a predefined ChatServerKey
	 */
	void login(ChatServerKey connectionKey, AsyncCallback<String> callback);

	void logout(ChatServerKey connectionKey, AsyncCallback<Boolean> callback);

	void getBuddyList(ChatServerKey connectionKey, AsyncCallback<ClientBuddy[]> callback);

	void getBuddyListUpdates(ChatServerKey connectionKey, AsyncCallback<ClientBuddy[]> callback);

	void addBuddy(ChatServerKey connectionKey, ClientBuddy buddy, AsyncCallback<Void> callback);

	void removeBuddy(ChatServerKey connectionKey, ClientBuddy buddy, AsyncCallback<Void> callback);

	void createChat(ChatServerKey connectionKey, String username, AsyncCallback<ClientChat> callback);

	void getIncomingChats(ChatServerKey connectionKey, AsyncCallback<ClientChat[]> callback);

	void sendMessage(ChatServerKey connectionKey, ClientChat chat, String message, AsyncCallback<Void> callback);

	void getMessages(ChatServerKey connectionKey, ClientChat chat, AsyncCallback<ClientMessage[]> callback);

	void register(String username, String password, AsyncCallback<Boolean> callback);
}
