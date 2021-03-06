package com.cheeonk.client.callback;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.cheeonk.shared.result.RegisterResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class Registered implements AsyncCallback<RegisterResult>
{
	@Override
	public void onFailure(Throwable oops)
	{
		Logger.getLogger("").log(Level.SEVERE, "Exception while executing " + Registered.class.getName() + ": " + oops.toString());
	}

	@Override
	public void onSuccess(RegisterResult result)
	{
		got(result.isRegistered());
	}

	public abstract void got(boolean isRegistered);
}
