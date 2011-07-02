package com.ryannadams.cheeonk.shared.buddy;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Presence implements IsSerializable
{
	public enum Mode
	{
		AVAILABLE, AWAY, CHAT, DND, XA;
	}

	public enum Type
	{

	}

	private Mode mode;

	public Presence()
	{
		this.mode = Mode.AVAILABLE;
	}

	public void setMode(Mode mode)
	{
		this.mode = mode;
	}

	public Mode getMode()
	{
		return mode;
	}

}
