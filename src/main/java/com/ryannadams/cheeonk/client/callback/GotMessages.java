package com.ryannadams.cheeonk.client.callback;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.shared.message.CheeonkMessage;
import com.ryannadams.cheeonk.shared.result.GetMessageResult;

public abstract class GotMessages implements AsyncCallback<GetMessageResult>
{

	@Override
	public void onFailure(Throwable caught)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(GetMessageResult result)
	{
		got(result.getMessages().toArray(new CheeonkMessage[result.getMessages().size()]));
	}

	public abstract void got(CheeonkMessage[] messages);
}
