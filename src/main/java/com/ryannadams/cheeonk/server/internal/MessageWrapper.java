package com.ryannadams.cheeonk.server.internal;

import org.jivesoftware.smack.packet.Message;

import com.ryannadams.cheeonk.client.chat.ClientMessage;

public class MessageWrapper extends Transmitted
{
	private final Message message;

	public MessageWrapper(Message message)
	{
		super();

		this.message = message;
	}

	// TODO: don't do this, actually wrap the message object
	public Message getMessage()
	{
		return message;
	}

	public ClientMessage getClientMessage()
	{
		return new ClientMessage(message.getBody(), message.getTo(),
				message.getFrom());
	}

	@Override
	public boolean equals(Object obj)
	{
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

}
