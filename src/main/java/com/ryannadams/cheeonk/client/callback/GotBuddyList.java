package com.ryannadams.cheeonk.client.callback;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.shared.buddy.IBuddy;
import com.ryannadams.cheeonk.shared.result.GetBuddyResult;

public abstract class GotBuddyList implements AsyncCallback<GetBuddyResult>
{
	@Override
	public void onFailure(Throwable caught)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(GetBuddyResult result)
	{
		got(result.getBuddyList().toArray(new IBuddy[result.getBuddyList().size()]));
	}

	public abstract void got(IBuddy[] buddies);
}
