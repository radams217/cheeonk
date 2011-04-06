package com.ryannadams.cheeonk.server.internal;

import org.jivesoftware.smack.packet.Message;

import com.ryannadams.cheeonk.client.IMessage;

public class MessageWrapperImpl implements IMessage
{
	private Message message;
	private boolean messageTransmitted;

	public MessageWrapperImpl(Message message)
	{
		this.message = message;
		messageTransmitted = false;
	}

	// public Message getMessage()
	// {
	// return message;
	// }

	public boolean isMessageTransmitted()
	{
		return messageTransmitted;
	}

	public void setMessageTransmitted(boolean messageTransmitted)
	{
		this.messageTransmitted = messageTransmitted;
	}

	@Override
	public String getBody()
	{
		// TODO Auto-generated method stub
		return message.getBody();
	}

	@Override
	public String getTo()
	{
		// TODO Auto-generated method stub
		return message.getTo();
	}

	@Override
	public String getFrom()
	{
		// TODO Auto-generated method stub
		return message.getFrom();
	}

}
