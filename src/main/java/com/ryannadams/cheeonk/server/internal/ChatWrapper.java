package com.ryannadams.cheeonk.server.internal;

import org.jivesoftware.smack.Chat;

import com.ryannadams.cheeonk.client.chat.ClientChat;

public class ChatWrapper extends Transmitted
{
	private final Chat chat;

	public ChatWrapper(Chat chat)
	{
		super();

		this.chat = chat;
	}

	// TODO: don't do this, actually wrap the chat object
	public Chat getChat()
	{
		return chat;
	}

	public ClientChat getClientChat()
	{
		return new ClientChat(chat.getParticipant());
	}

}
