package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.buddy.CheeonkPresence;
import com.ryannadams.cheeonk.shared.result.ChangePresenceResult;

public class ChangePresence implements Action<ChangePresenceResult>, IKey
{
	private ConnectionKey key;
	private CheeonkPresence presence;

	@Deprecated
	public ChangePresence()
	{

	}

	public ChangePresence(ConnectionKey key, CheeonkPresence presence)
	{
		this.key = key;
		this.presence = presence;
	}

	@Override
	public ConnectionKey getConnectionKey()
	{
		return key;
	}

	public CheeonkPresence getPresence()
	{
		return presence;
	}

}
