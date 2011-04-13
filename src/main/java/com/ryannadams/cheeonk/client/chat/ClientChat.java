package com.ryannadams.cheeonk.client.chat;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientChat extends AbstractChat implements IsSerializable
{
	protected String participant;
	protected String threadID;

	public ClientChat()
	{

	}

	public ClientChat(String threadID)
	{
		this.threadID = threadID;
	}

	public void setParticipant(String participant)
	{
		this.participant = participant;
	}

	// @Override
	// public String getParticipant()
	// {
	// return participant;
	// }

	@Override
	public String getThreadID()
	{
		return threadID;
	}

}