package com.ryannadams.cheeonk.shared.event;

import com.ryannadams.cheeonk.shared.message.CheeonkMessage;

public class MessageReceivedEvent2 implements CheeonkEvent
{
	private CheeonkMessage message;

	@Deprecated
	public MessageReceivedEvent2()
	{

	}

	public MessageReceivedEvent2(CheeonkMessage message)
	{
		this.message = message;
	}

	public CheeonkMessage getMessage()
	{
		return message;
	}

}
