package com.cheeonk.shared.result;

import net.customware.gwt.dispatch.shared.Result;

import com.cheeonk.shared.buddy.JabberId;

public class SigninResult implements Result
{
	private String connectionId;
	private JabberId jabberId;
	private boolean isConnected;
	private boolean isSignedin;

	public SigninResult()
	{
		connectionId = null;
		this.isConnected = false;
		this.isSignedin = false;
	}

	public SigninResult(String connectionId, JabberId jabberId, boolean isConnected, boolean isSignedin)
	{
		this.connectionId = connectionId;
		this.jabberId = jabberId;
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

	public JabberId getJabberId()
	{
		return jabberId;
	}

	public void setJabberId(JabberId jabberId)
	{
		this.jabberId = jabberId;
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
