package com.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.result.SignoutResult;

public class Signout implements Action<SignoutResult>, IKey
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

	@Override
	public ConnectionKey getConnectionKey()
	{
		return key;
	}

}
