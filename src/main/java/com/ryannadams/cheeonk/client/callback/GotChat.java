package com.ryannadams.cheeonk.client.callback;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.shared.chat.CheeonkChat;
import com.ryannadams.cheeonk.shared.result.GetChatResult;

public abstract class GotChat implements AsyncCallback<GetChatResult>
{

	@Override
	public void onFailure(Throwable caught)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(GetChatResult result)
	{
		got(result.getChats().toArray(new CheeonkChat[result.getChats().size()]));
	}

	public abstract void got(CheeonkChat[] chats);
}
