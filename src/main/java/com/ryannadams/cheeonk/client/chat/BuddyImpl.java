package com.ryannadams.cheeonk.client.chat;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BuddyImpl implements IBuddy, IsSerializable
{
	private String name;
	private String user;

	public BuddyImpl()
	{

	}

	public BuddyImpl(String name, String user)
	{
		this.name = name;
		this.user = user;
	}

	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getUser()
	{
		// TODO Auto-generated method stub
		return user;
	}

}
