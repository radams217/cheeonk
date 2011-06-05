package com.ryannadams.cheeonk.client.callback;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.shared.result.GetBuddyPresenceResult;

public abstract class GotBuddyPresence implements AsyncCallback<GetBuddyPresenceResult>
{
	@Override
	public void onFailure(Throwable caught)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void onSuccess(GetBuddyPresenceResult result)
	{
		got(result.isAvailable());
	}

	public abstract void got(boolean isAvailable);
}
