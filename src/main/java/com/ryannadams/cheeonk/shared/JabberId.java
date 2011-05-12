package com.ryannadams.cheeonk.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class JabberId implements IsSerializable
{
	private String jabberId;

	public JabberId()
	{

	}

	public JabberId(String jabberId)
	{
		this.jabberId = jabberId;
	}

	public String getJabberId()
	{
		return jabberId;
	}
}
