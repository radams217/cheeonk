package com.ryannadams.cheeonk.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ryannadams.cheeonk.client.chat.IBuddy;
import com.ryannadams.cheeonk.client.chat.IChat;
import com.ryannadams.cheeonk.client.chat.IMessage;

@RemoteServiceRelativePath("chat")
public interface ChatService extends RemoteService
{
	Boolean login(String username, String password);

	Boolean logout();

	IBuddy[] getBuddyList();

	void addBuddy(IBuddy buddy);

	void removeBuddy(IBuddy buddy);

	void createChat(String recipient);

	void sendMessage(IChat key, String message);

	IMessage[] getMessages(IChat key);
}
