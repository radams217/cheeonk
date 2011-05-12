package com.ryannadams.cheeonk.client.callback;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.shared.message.CheeonkMessage;
import com.ryannadams.cheeonk.shared.result.GetMessagesResult;

public abstract class GotMessages implements AsyncCallback<GetMessagesResult>
{

	@Override
	public void onFailure(Throwable caught)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(GetMessagesResult result)
	{
		got(result.getMessages());
	}

	public abstract void got(CheeonkMessage[] messages);
}
