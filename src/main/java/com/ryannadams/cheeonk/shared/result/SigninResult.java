package com.ryannadams.cheeonk.shared.result;

import net.customware.gwt.dispatch.shared.Result;

public class SigninResult implements Result
{
	private String connectionId;
	private boolean isConnected;
	private boolean isSignedin;

	public SigninResult()
	{
		connectionId = null;
		this.isConnected = false;
		this.isSignedin = false;
	}

	public SigninResult(String connectionId, boolean isConnected, boolean isSignedin)
	{
		this.connectionId = connectionId;
		this.isConnected = isConnected;
		this.isSignedin = isSignedin;
	}

	public String getConnectionId()
	{
		return connectionId;
	}

	public void setConnectionId(String connectionId)
	{
		this.connectionId = connectionId;
	}

	public boolean isConnected()
	{
		return isConnected;
	}

	public void setConnected(boolean isConnected)
	{
		this.isConnected = isConnected;
	}

	public boolean isSignedin()
	{
		return isSignedin;
	}

	public void setSignedin(boolean isSignedin)
	{
		this.isSignedin = isSignedin;
	}

}
