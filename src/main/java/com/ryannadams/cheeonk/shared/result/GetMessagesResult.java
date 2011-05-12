package com.ryannadams.cheeonk.shared.result;

import net.customware.gwt.dispatch.shared.Result;

import com.ryannadams.cheeonk.shared.message.CheeonkMessage;

public class GetMessagesResult implements Result
{
	private CheeonkMessage[] messages;

	@Deprecated
	public GetMessagesResult()
	{

	}

	public GetMessagesResult(CheeonkMessage[] messages)
	{
		this.messages = messages;
	}

	public CheeonkMessage[] getMessages()
	{
		return messages;
	}

}
