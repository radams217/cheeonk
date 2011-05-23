package com.ryannadams.cheeonk.shared.result;

import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;

import com.ryannadams.cheeonk.shared.message.CheeonkMessage;

public class GetMessageResult implements Result
{
	private ArrayList<CheeonkMessage> messages;

	public GetMessageResult()
	{
		this.messages = new ArrayList<CheeonkMessage>();
	}

	public GetMessageResult(ArrayList<CheeonkMessage> messages)
	{
		this.messages = messages;
	}

	public void addMessage(CheeonkMessage message)
	{
		messages.add(message);
	}

	public ArrayList<CheeonkMessage> getMessages()
	{
		return new ArrayList<CheeonkMessage>(messages);
	}

}
