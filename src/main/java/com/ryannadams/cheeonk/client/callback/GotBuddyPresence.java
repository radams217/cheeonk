package com.ryannadams.cheeonk.client.callback;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.shared.Presence;
import com.ryannadams.cheeonk.shared.result.GetBuddyPresenceResult;

public abstract class GotBuddyPresence implements AsyncCallback<GetBuddyPresenceResult>
{
	@Override
	public void onFailure(Throwable oops)
	{
		Logger.getLogger("").log(Level.SEVERE, "Exception while executing " + GotBuddyPresence.class.getName() + ": " + oops.toString());

	}

	@Override
	public void onSuccess(GetBuddyPresenceResult result)
	{
		got(result.getPresence());
	}

	public abstract void got(Presence presence);

}
