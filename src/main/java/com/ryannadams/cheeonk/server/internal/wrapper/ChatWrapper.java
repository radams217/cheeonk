package com.ryannadams.cheeonk.server.internal.wrapper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import com.ryannadams.cheeonk.shared.chat.AbstractChat;
import com.ryannadams.cheeonk.shared.chat.ClientChat;

public class ChatWrapper extends AbstractChat
{
	private final Chat chat;
	private boolean isTransmitted;

	public ChatWrapper(Chat chat)
	{
		this.chat = chat;
		this.isTransmitted = false;
	}

	@Override
	public String getThreadID()
	{
		return chat.getThreadID();
	}

	@Override
	public String getParticipant()
	{
		return chat.getParticipant();
	}

	public boolean isTransmitted()
	{
		return isTransmitted;
	}

	public void setTransmitted(boolean isTransmitted)
	{
		this.isTransmitted = isTransmitted;
	}

	public void sendMessage(String text) throws XMPPException
	{
		chat.sendMessage(text);
	}

	public ClientChat getClientChat()
	{
		ClientChat clientChat = new ClientChat(getThreadID());
		clientChat.setParticipant(getParticipant());

		return clientChat;
	}

}
