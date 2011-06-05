package com.ryannadams.cheeonk.shared.result;

import net.customware.gwt.dispatch.shared.Result;

public class GetBuddyPresenceResult implements Result
{
	private boolean isAvailable;

	@Deprecated
	public GetBuddyPresenceResult()
	{

	}

	public GetBuddyPresenceResult(boolean isAvailable)
	{
		this.isAvailable = isAvailable;
	}

	public boolean isAvailable()
	{
		return isAvailable;
	}

}
