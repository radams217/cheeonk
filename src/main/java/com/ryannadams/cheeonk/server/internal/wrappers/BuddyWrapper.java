package com.ryannadams.cheeonk.server.internal.wrappers;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.packet.Presence;

import com.ryannadams.cheeonk.shared.buddy.AbstractBuddy;
import com.ryannadams.cheeonk.shared.buddy.CheeonkBuddy;

// Wraps the RosterEntry object, but I hate that naming so I am changing it to buddy
public class BuddyWrapper extends AbstractBuddy
{
	private final RosterEntry buddy;
	private Presence presence;

	public BuddyWrapper(RosterEntry buddy)
	{
		super();

		this.buddy = buddy;
	}

	public void setPresence(Presence presence)
	{
		this.presence = presence;
	}

	@Override
	public String getJID()
	{
		return buddy.getUser();
	}

	@Override
	public String getName()
	{
		return buddy.getName();
	}

	@Override
	public boolean isAvailable()
	{
		return presence.isAvailable();
	}

	@Override
	public boolean isAway()
	{
		return presence.isAway();
	}

	public CheeonkBuddy getClientBuddy()
	{
		return new CheeonkBuddy(getJID(), getName(), isAvailable());
	}

}
