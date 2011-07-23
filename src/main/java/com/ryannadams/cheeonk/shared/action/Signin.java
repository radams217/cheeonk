package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.result.SigninResult;

public class Signin implements Action<SigninResult>, IKey
{
	private String username;
	private String password;
	private ConnectionKey key;

	@Deprecated
	public Signin()
	{

	}

	public Signin(String username, String password)
	{
		this.username = username;
		this.password = password;
		this.key = ConnectionKey.get();
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	@Override
	public ConnectionKey getConnectionKey()
	{
		return key;
	}

}
