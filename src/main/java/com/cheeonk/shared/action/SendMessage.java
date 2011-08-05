package com.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.message.IMessage;
import com.cheeonk.shared.result.SendMessageResult;

public class SendMessage implements Action<SendMessageResult>, IKey
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

	@Override
	public ConnectionKey getConnectionKey()
	{
		return key;
	}

	public IMessage getMessage()
	{
		return message;
	}

}
