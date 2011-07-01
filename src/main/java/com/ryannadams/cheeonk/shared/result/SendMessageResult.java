package com.ryannadams.cheeonk.shared.result;

import net.customware.gwt.dispatch.shared.Result;

import com.ryannadams.cheeonk.shared.message.IMessage;

public class SendMessageResult implements Result
{
	private IMessage message;

	@Deprecated
	public SendMessageResult()
	{

	}

	public SendMessageResult(IMessage message)
	{
		this.message = message;
	}

	public IMessage getMessage()
	{
		return message;
	}

}
