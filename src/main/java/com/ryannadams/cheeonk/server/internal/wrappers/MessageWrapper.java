package com.ryannadams.cheeonk.server.internal.wrappers;

import org.jivesoftware.smack.packet.Message;

import com.ryannadams.cheeonk.shared.message.ClientMessage;
import com.ryannadams.cheeonk.shared.message.IMessage;

public class MessageWrapper implements IMessage
{
	private final Message message;

	public MessageWrapper(Message message)
	{
		super();

		this.message = message;
	}

	@Override
	public String getBody()
	{
		return message.getBody();
	}

	@Override
	public String getTo()
	{
		return message.getTo();
	}

	@Override
	public String getFrom()
	{
		return message.getFrom();
	}

	public ClientMessage getClientMessage()
	{
		return new ClientMessage(getBody(), getTo(), getFrom());
	}
}
