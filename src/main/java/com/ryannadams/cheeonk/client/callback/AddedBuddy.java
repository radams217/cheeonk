package com.ryannadams.cheeonk.client.callback;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.shared.result.AddBuddyResult;

public abstract class AddedBuddy implements AsyncCallback<AddBuddyResult>
{
	@Override
	public void onFailure(Throwable caught)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(AddBuddyResult result)
	{
		got();

	}

	public abstract void got();

}
