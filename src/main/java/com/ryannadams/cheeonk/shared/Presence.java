package com.ryannadams.cheeonk.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Presence implements IsSerializable
{
	private String presence;

	public Presence()
	{

	}

	public Presence(String presence)
	{
		this.presence = presence;
	}

	public String getPresence()
	{
		return presence;
	}

}
