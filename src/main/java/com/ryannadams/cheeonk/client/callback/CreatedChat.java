package com.ryannadams.cheeonk.client.callback;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.shared.chat.CheeonkChat;
import com.ryannadams.cheeonk.shared.result.GetChatResult;

public abstract class CreatedChat implements AsyncCallback<GetChatResult>
{
	@Override
	public void onFailure(Throwable caught)
	{
		Logger.getLogger("").log(Level.SEVERE, caught.getMessage());
		caught.printStackTrace();
	}

	@Override
	public void onSuccess(GetChatResult result)
	{
		got(result.getChats().toArray(new CheeonkChat[result.getChats().size()]));
	}

	public abstract void got(CheeonkChat[] chats);
}
