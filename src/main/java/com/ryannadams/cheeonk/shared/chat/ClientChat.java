package com.ryannadams.cheeonk.shared.chat;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientChat extends AbstractChat implements IsSerializable
{
	private String threadID;
	private String participant;

	public ClientChat()
	{

	}

	public ClientChat(String threadID)
	{
		this.threadID = threadID;
	}

	@Override
	public String getThreadID()
	{
		return threadID;
	}

	@Override
	public String getParticipant()
	{
		return participant;
	}

	public void setParticipant(String participant)
	{
		this.participant = participant;
	}
}