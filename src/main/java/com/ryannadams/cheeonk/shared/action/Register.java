package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.result.RegisterResult;

public class Register implements Action<RegisterResult>
{
	private String username;
	private String password;
	private String name;
	private String email;

	@Deprecated
	public Register()
	{

	}

	public Register(String username, String password, String name, String email)
	{
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public String getName()
	{
		return name;
	}

	public String getEmail()
	{
		return email;
	}
}
