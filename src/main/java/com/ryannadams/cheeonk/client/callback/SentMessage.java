package com.ryannadams.cheeonk.client.callback;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.shared.message.IMessage;
import com.ryannadams.cheeonk.shared.result.SendMessageResult;

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
