package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.result.RegisterResult;

public class Register implements Action<RegisterResult>
{
	private String username;
	private String password;

	@Deprecated
	public Register()
	{

	}

	public Register(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

}
