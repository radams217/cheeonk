package com.ryannadams.cheeonk.client.message;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientMessage implements IMessage, IsSerializable
{
	protected String body;
	protected String to;
	protected String from;

	public ClientMessage()
	{

	}

	public ClientMessage(String body, String to, String from)
	{
		this.body = body;
		this.to = to;
		this.from = from;
	}

	@Override
	public String getBody()
	{
		return body;
	}

	@Override
	public String getTo()
	{
		return to;
	}

	@Override
	public String getFrom()
	{
		return from;
	}

}
