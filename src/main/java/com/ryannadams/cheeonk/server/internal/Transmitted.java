package com.ryannadams.cheeonk.server.internal;

public class Transmitted
{
	protected boolean transmitted;

	public Transmitted()
	{
		this.transmitted = false;
	}

	public boolean isTransmitted()
	{
		return transmitted;
	}

	public void setTransmitted(boolean transmitted)
	{
		this.transmitted = transmitted;
	}

}
