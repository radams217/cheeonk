package com.ryannadams.cheeonk.client.chat;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientBuddy implements IsSerializable
{
	protected String name;
	protected String user;
	protected boolean isAvailable;

	public ClientBuddy()
	{

	}

	public ClientBuddy(String name, String user, boolean isAvailable)
	{
		this.name = name;
		this.user = user;
		this.isAvailable = isAvailable;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public String getName()
	{
		return name;
	}

	public String getUser()
	{
		return user;
	}

	public boolean isAvailable()
	{
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable)
	{
		this.isAvailable = isAvailable;
	}

}
