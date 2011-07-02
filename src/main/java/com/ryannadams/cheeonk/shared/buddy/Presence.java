package com.ryannadams.cheeonk.shared.buddy;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Presence implements IsSerializable
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

	public Presence()
	{
		this.type = Type.UNAVAILABLE;
		this.mode = null;
	}

	public Presence(Type type, Mode mode)
	{
		this.type = type;
		this.mode = mode;
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

	public boolean isAvailable()
	{
		return Type.AVAILABLE.equals(type);
	}

	public boolean isAway()
	{
		return Mode.AWAY.equals(mode);
	}

	public static Presence getPresence(org.jivesoftware.smack.packet.Presence presence)
	{
		Presence cheeonkPresence = new Presence();

		switch (presence.getType())
		{
			case available:
				cheeonkPresence.setType(Presence.Type.AVAILABLE);
				break;

		}

		switch (presence.getMode())
		{
			case available:
				cheeonkPresence.setMode(Presence.Mode.AVAILABLE);
				break;

		}

		return new Presence();
	}

}
