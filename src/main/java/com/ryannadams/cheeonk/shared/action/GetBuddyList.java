package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.result.GetBuddyListResult;

public class GetBuddyList implements Action<GetBuddyListResult>
{
	private ConnectionKey key;

	@Deprecated
	public GetBuddyList()
	{

	}

	public GetBuddyList(ConnectionKey key)
	{
		this.key = key;
	}

	public ConnectionKey getConnectionKey()
	{
		return key;
	}

}