package com.ryannadams.cheeonk.shared.buddy;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CheeonkPresence implements IsSerializable
{
	private JabberId jID;

	public enum Type
	{
		AVAILABLE, UNAVAILABLE, SUBSCRIBE, SUBSCRIBED, UNSUBSCRIBE, UNSUBSCRIBED, ERROR;
	}

	public enum Mode
	{
		AVAILABLE, UNAVAILABLE, AWAY, CHAT, DND, XA;
	}

	private Type type;
	private Mode mode;
	private String status;

	@Deprecated
	public CheeonkPresence()
	{

	}

	public CheeonkPresence(JabberId jID)
	{
		this.jID = jID;
		this.type = Type.UNAVAILABLE;
		this.mode = Mode.UNAVAILABLE;
		this.status = "";
	}

	public CheeonkPresence(JabberId jID, Mode mode, String status)
	{
		this.jID = jID;
		this.type = Type.AVAILABLE;
		this.mode = mode;
		this.status = status;
	}

	public CheeonkPresence(JabberId jID, Type type, Mode mode, String status)
	{
		this.jID = jID;
		this.type = type;
		this.mode = mode;
		this.status = status;
	}

	public JabberId getJabberId()
	{
		return jID;
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

	// /**
	// * @return the current presence/status of the user
	// */
	// CheeonkPresence getPresence();
	//
	// /**
	// * @param presence
	// * sets the serializable shared presence object
	// */
	// void setPresence(CheeonkPresence presence);
	//
	// /**
	// * @return true/false depending on if the user is available on the server.
	// */
	// boolean isAvailable();
	//
	// /**
	// * @return true/false depending on if the user is available but away or
	// not
	// * available.
	// */
	// boolean isAway();
}
