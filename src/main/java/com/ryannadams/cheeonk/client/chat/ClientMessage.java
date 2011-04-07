package com.ryannadams.cheeonk.client.chat;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientMessage implements IsSerializable
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

	public String getBody()
	{
		return body;
	}

	public String getTo()
	{
		return to;
	}

	public String getFrom()
	{
		return from;
	}

}
