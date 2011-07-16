package com.ryannadams.cheeonk.shared.buddy;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CheeonkPresence implements IsSerializable
{
	public enum Type
	{
		AVAILABLE, UNAVAILABLE, SUBSCRIBE, SUBSCRIBED, UNSUBSCRIBE, UNSUBSCRIBED, ERROR;
	}

	public enum Mode
	{
		AVAILABLE, AWAY, CHAT, DND, XA;
	}

	private Type type;
	private Mode mode;
	private String status;

	public CheeonkPresence()
	{
		this.type = Type.UNAVAILABLE;
		this.mode = null;
		this.status = "";
	}

	public CheeonkPresence(Type type, String status, Mode mode)
	{
		this.type = type;
		this.mode = mode;
		this.status = status;
	}

	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	public void setMode(Mode mode)
	{
		this.mode = mode;
	}

	public Mode getMode()
	{
		return mode;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public boolean isAvailable()
	{
		return Type.AVAILABLE.equals(type);
	}

	public boolean isAway()
	{
		return Mode.AWAY.equals(mode);
	}

}
