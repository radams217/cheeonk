package com.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.buddy.IBuddy;
import com.cheeonk.shared.result.AddBuddyResult;

public class AddBuddy implements Action<AddBuddyResult>, IKey
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

	@Override
	public ConnectionKey getConnectionKey()
	{
		return key;
	}

	public IBuddy getBuddy()
	{
		return buddy;
	}
}
