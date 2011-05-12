package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.JabberId;
import com.ryannadams.cheeonk.shared.result.GetBuddyPresenceResult;

/**
 * @author radams217
 * 
 */
public class GetBuddyPresence implements Action<GetBuddyPresenceResult>
{
	private JabberId id;
	private ConnectionKey key;

	@Deprecated
	public GetBuddyPresence()
	{

	}

	public GetBuddyPresence(ConnectionKey key, JabberId id)
	{
		this.key = key;
		this.id = id;
	}

	public ConnectionKey getConnectionKey()
	{
		return key;
	}

	public JabberId getId()
	{
		return id;
	}

}
