package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.chat.CheeonkChat;
import com.ryannadams.cheeonk.shared.result.GetMessageResult;

public class GetMessages implements Action<GetMessageResult>
{
	private ConnectionKey key;
	private CheeonkChat chat;

	@Deprecated
	public GetMessages()
	{

	}

	public GetMessages(ConnectionKey key, CheeonkChat chat)
	{
		this.key = key;
		this.chat = chat;
	}

	public ConnectionKey getConnectionKey()
	{
		return key;
	}

	public CheeonkChat getChat()
	{
		return chat;
	}

}
