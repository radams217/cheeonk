package com.ryannadams.cheeonk.client.chat;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientChat extends AbstractChat implements IsSerializable
{
	protected String participant;

	public ClientChat()
	{

	}

	public ClientChat(String participant)
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