package com.ryannadams.cheeonk.client.chat;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ChatImpl implements IChat, IsSerializable
{
	private String participant;

	public ChatImpl()
	{

	}

	public ChatImpl(String participant)
	{
		this.participant = participant;
	}

	public void setParticipant(String participant)
	{
		this.participant = participant;
	}

	@Override
	public String getParticipant()
	{
		return participant;
	}

}