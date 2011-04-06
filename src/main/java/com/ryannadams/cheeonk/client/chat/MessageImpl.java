package com.ryannadams.cheeonk.client.chat;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MessageImpl implements IMessage, IsSerializable
{
	private String body;
	private String to;
	private String from;

	public MessageImpl()
	{

	}

	public MessageImpl(String body, String to, String from)
	{
		this.body = body;
		this.to = to;
		this.from = from;
	}

	@Override
	public String getBody()
	{
		// TODO Auto-generated method stub
		return body;
	}

	@Override
	public String getTo()
	{
		// TODO Auto-generated method stub
		return to;
	}

	@Override
	public String getFrom()
	{
		// TODO Auto-generated method stub
		return from;
	}

}
