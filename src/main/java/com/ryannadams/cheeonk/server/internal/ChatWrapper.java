package com.ryannadams.cheeonk.server.internal;

import org.jivesoftware.smack.Chat;

import com.ryannadams.cheeonk.client.chat.AbstractChat;
import com.ryannadams.cheeonk.client.chat.ClientChat;
import com.ryannadams.cheeonk.client.chat.IChat;

public class ChatWrapper extends AbstractChat implements IChat
{
	private final Chat chat;
	private final Transmitted transmitted;

	public ChatWrapper(Chat chat)
	{
		this.chat = chat;
		this.transmitted = new Transmitted();
	}

	// TODO: don't do this, actually wrap the chat object
	public Chat getChat()
	{
		return chat;
	}

	@Override
	public String getParticipant()
	{
		return chat.getParticipant();
	}

	public ClientChat getClientChat()
	{
		return new ClientChat(chat.getParticipant());
	}

	public boolean isTransmitted()
	{
		return transmitted.isTransmitted();
	}

	public void setTransmitted(boolean transmitted)
	{
		this.transmitted.setTransmitted(transmitted);
	}

}
