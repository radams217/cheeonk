package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.message.IMessage;
import com.ryannadams.cheeonk.shared.result.SendMessageResult;

public class SendMessage implements Action<SendMessageResult>
{
	private ConnectionKey key;
	private IMessage message;

	@Deprecated
	public SendMessage()
	{

	}

	public SendMessage(ConnectionKey key, IMessage message)
	{
		this.key = key;
		this.message = message;
	}

	public ConnectionKey getConnectionKey()
	{
		return key;
	}

	public IMessage getMessage()
	{
		return message;
	}

}
