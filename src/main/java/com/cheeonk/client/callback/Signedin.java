package com.cheeonk.client.callback;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.cheeonk.shared.buddy.JabberId;
import com.cheeonk.shared.result.SigninResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

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
		got(result.getConnectionId(), result.getJabberId(), result.isConnected(), result.isSignedin());
	}

	public abstract void got(String connectionId, JabberId jabberId, boolean isConnected, boolean isSignedin);
}
