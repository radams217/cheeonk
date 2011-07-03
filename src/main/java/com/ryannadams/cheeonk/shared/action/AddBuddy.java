package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.buddy.IBuddy;
import com.ryannadams.cheeonk.shared.result.AddBuddyResult;

public class AddBuddy implements Action<AddBuddyResult>
{
	private ConnectionKey key;
	private IBuddy buddy;

	@Deprecated
	public AddBuddy()
	{

	}

	public AddBuddy(ConnectionKey key, IBuddy buddy)
	{
		this.key = key;
		this.buddy = buddy;
	}

	public ConnectionKey getConnectionKey()
	{
		return key;
	}

	public IBuddy getBuddy()
	{
		return buddy;
	}
}
