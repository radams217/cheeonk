package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.JabberId;
import com.ryannadams.cheeonk.shared.result.GetBuddyPresenceResult;

public class GetBuddyPresence implements Action<GetBuddyPresenceResult>
{
	private ConnectionKey key;
	public JabberId jabberId;

	@Deprecated
	public GetBuddyPresence()
	{

	}

	public GetBuddyPresence(ConnectionKey key, JabberId jabberId)
	{
		this.jabberId = jabberId;
		this.key = key;
	}

	public JabberId getJabberId()
	{
		return jabberId;
	}

	public ConnectionKey getConnectionKey()
	{
		return key;
	}

}
