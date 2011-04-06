package com.ryannadams.cheeonk.server.internal;

import org.jivesoftware.smack.Chat;

import com.ryannadams.cheeonk.client.IChat;

public class ChatWrapperImpl implements IChat
{
	private Chat chat;

	public ChatWrapperImpl(Chat chat)
	{
		this.chat = chat;
	}

	public Chat getChat()
	{
		return chat;
	}

	@Override
	public String getParticipant()
	{
		return chat.getParticipant();
	}

}
