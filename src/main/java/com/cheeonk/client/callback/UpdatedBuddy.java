package com.cheeonk.client.callback;

import com.cheeonk.shared.result.UpdateBuddyResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class UpdatedBuddy implements AsyncCallback<UpdateBuddyResult>
{

	@Override
	public void onFailure(Throwable caught)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(UpdateBuddyResult result)
	{
		got();
	}

	public abstract void got();
}
