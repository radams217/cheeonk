package com.ryannadams.cheeonk.server.internal;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.packet.Presence;

import com.ryannadams.cheeonk.client.chat.ClientBuddy;

// Wraps the RosterEntry object, but I hate that naming so I am changing it to buddy
public class BuddyWrapper extends Transmitted
{
	private final RosterEntry buddy;
	private Presence presence;

	public BuddyWrapper(RosterEntry buddy, Presence presence)
	{
		super();

		this.buddy = buddy;
		this.presence = presence;
	}

	public String getUser()
	{
		return buddy.getUser();
	}

	public String getName()
	{
		return buddy.getName();
	}

	public Presence getPresence()
	{
		return presence;
	}

	public void setPresence(Presence presence)
	{
		this.presence = presence;
	}

	public ClientBuddy getClientBuddy()
	{
		return new ClientBuddy(buddy.getName(), buddy.getUser(), presence.isAvailable());
	}
}
