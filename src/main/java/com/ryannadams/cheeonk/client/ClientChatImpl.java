package com.ryannadams.cheeonk.client;

public class ClientChatImpl implements IChat
{
	private String participant;

	public ClientChatImpl(String participant)
	{
		this.participant = participant;
	}

	@Override
	public String getParticipant()
	{
		return participant;
	}

}
