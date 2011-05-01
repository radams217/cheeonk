package com.ryannadams.cheeonk.shared.buddy;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientBuddy extends AbstractBuddy implements IsSerializable
{
	private String jID;
	private String name;
	private boolean isAvailable;
	private boolean isAway;

	public ClientBuddy()
	{

	}

	public ClientBuddy(String jID, String name, boolean isAvailable)
	{
		this.jID = jID;
		this.name = name;
		this.isAvailable = isAvailable;
		this.isAway = false;
	}

	@Override
	public String getJID()
	{
		return jID;
	}

	@Override
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public boolean isAvailable()
	{
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable)
	{
		this.isAvailable = isAvailable;
	}

	@Override
	public boolean isAway()
	{
		return isAway;
	}

	public void setAway(boolean isAway)
	{
		this.isAway = isAway;
	}

}
