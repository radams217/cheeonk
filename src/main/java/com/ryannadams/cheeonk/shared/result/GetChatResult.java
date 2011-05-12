package com.ryannadams.cheeonk.shared.result;

import net.customware.gwt.dispatch.shared.Result;

import com.ryannadams.cheeonk.shared.chat.CheeonkChat;

public class GetChatResult implements Result
{
	private CheeonkChat[] chats;

	@Deprecated
	public GetChatResult()
	{

	}

	public GetChatResult(CheeonkChat[] chatList)
	{
		this.chats = chatList;
	}

	public GetChatResult(CheeonkChat chat)
	{
		this.chats = new CheeonkChat[] { chat };
	}

	public CheeonkChat[] getChats()
	{
		return chats;
	}
}
