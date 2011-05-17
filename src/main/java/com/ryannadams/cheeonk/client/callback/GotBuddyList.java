package com.ryannadams.cheeonk.client.callback;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.shared.buddy.CheeonkBuddy;
import com.ryannadams.cheeonk.shared.result.GetBuddyListResult;

public abstract class GotBuddyList implements AsyncCallback<GetBuddyListResult>
{
	@Override
	public void onFailure(Throwable caught)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(GetBuddyListResult result)
	{
		got(result.getBuddyList());
	}

	public abstract void got(CheeonkBuddy[] buddies);
}
