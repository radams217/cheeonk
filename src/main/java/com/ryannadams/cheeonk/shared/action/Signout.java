package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.result.SignoutResult;

public class Signout implements Action<SignoutResult>
{
	private ConnectionKey key;

	@Deprecated
	public Signout()
	{

	}

	public Signout(ConnectionKey key)
	{
		this.key = key;
	}

	public ConnectionKey getConnectionKey()
	{
		return key;
	}

}
