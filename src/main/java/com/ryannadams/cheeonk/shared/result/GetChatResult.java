package com.ryannadams.cheeonk.shared.result;

import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;

import com.ryannadams.cheeonk.shared.chat.CheeonkChat;

public class GetChatResult implements Result
{
	private ArrayList<CheeonkChat> chats;

	public GetChatResult()
	{
		this.chats = new ArrayList<CheeonkChat>();
	}

	public GetChatResult(ArrayList<CheeonkChat> chats)
	{
		this.chats = chats;
	}

	public void addChat(CheeonkChat chat)
	{
		chats.add(chat);
	}

	public ArrayList<CheeonkChat> getChats()
	{
		return new ArrayList<CheeonkChat>(chats);
	}
}
