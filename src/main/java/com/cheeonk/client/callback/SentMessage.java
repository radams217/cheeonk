package com.cheeonk.client.callback;

import com.cheeonk.shared.message.IMessage;
import com.cheeonk.shared.result.SendMessageResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class SentMessage implements AsyncCallback<SendMessageResult>
{
	@Override
	public void onFailure(Throwable caught)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(SendMessageResult result)
	{
		got(result.getMessage());
	}

	public abstract void got(IMessage message);
}
