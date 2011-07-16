package com.ryannadams.cheeonk.client.callback;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.shared.result.ChangePresenceResult;

public abstract class ChangedPresence implements AsyncCallback<ChangePresenceResult>
{
	@Override
	public void onFailure(Throwable caught)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(ChangePresenceResult result)
	{
		got();
	}

	public abstract void got();
}