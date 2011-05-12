package com.ryannadams.cheeonk.shared.chat;

public class CheeonkChat extends AbstractChat
{
	private String threadID;
	private String participant;

	@Deprecated
	public CheeonkChat()
	{

	}

	public CheeonkChat(String threadID)
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
