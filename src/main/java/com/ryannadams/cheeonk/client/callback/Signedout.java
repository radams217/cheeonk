package com.ryannadams.cheeonk.client.callback;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.shared.result.SignoutResult;

public abstract class Signedout implements AsyncCallback<SignoutResult>
{
	@Override
	public void onFailure(Throwable oops)
	{
		Logger.getLogger("").log(Level.SEVERE, "Exception while executing " + Signedout.class.getName() + ": " + oops.toString());
	}

	@Override
	public void onSuccess(SignoutResult result)
	{
		got(result.isSignedout());
	}

	public abstract void got(boolean isSignedout);
}
