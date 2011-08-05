package com.cheeonk.client.callback;

import com.cheeonk.shared.buddy.CheeonkPresence;
import com.cheeonk.shared.result.ChangePresenceResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

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
		got(result.getPresence());
	}

	public abstract void got(CheeonkPresence cheeonkPresence);
}
