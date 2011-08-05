package com.cheeonk.client.callback;

import com.cheeonk.shared.result.AddBuddyResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

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
