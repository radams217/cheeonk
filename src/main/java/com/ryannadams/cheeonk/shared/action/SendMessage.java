package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.chat.CheeonkChat;
import com.ryannadams.cheeonk.shared.message.CheeonkMessage;
import com.ryannadams.cheeonk.shared.result.SendMessageResult;

public class SendMessage implements Action<SendMessageResult>
{
	private ConnectionKey key;
	private CheeonkChat chat;
	private CheeonkMessage message;

	@Deprecated
	public SendMessage()
	{

	}

	public SendMessage(ConnectionKey key, CheeonkChat chat, CheeonkMessage message)
	{
		this.key = key;
		this.chat = chat;
		this.message = message;
	}

	public ConnectionKey getConnectionKey()
	{
		return key;
	}

	public CheeonkChat getChat()
	{
		return chat;
	}

	public CheeonkMessage getMessage()
	{
		return message;
	}

}
