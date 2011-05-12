package com.ryannadams.cheeonk.client.callback;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.shared.result.SigninResult;

public abstract class Signedin implements AsyncCallback<SigninResult>
{
	@Override
	public void onFailure(Throwable oops)
	{
		Logger.getLogger("").log(Level.SEVERE, "Exception while executing " + Signedin.class.getName() + ": " + oops.toString());
	}

	@Override
	public void onSuccess(SigninResult result)
	{
		got(result.isSignedin());
	}

	public abstract void got(boolean isSignedin);
}
