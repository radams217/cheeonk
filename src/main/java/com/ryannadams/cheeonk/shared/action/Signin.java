package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.result.SigninResult;

public class Signin implements Action<SigninResult>, IKey
{
	private ConnectionKey key;

	@Deprecated
	public Signin()
	{

	}

	public Signin(ConnectionKey key)
	{
		this.key = key;
	}

	@Override
	public ConnectionKey getConnectionKey()
	{
		return key;
	}

}
