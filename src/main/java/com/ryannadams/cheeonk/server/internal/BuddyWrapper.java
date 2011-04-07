package com.ryannadams.cheeonk.server.internal;

import org.jivesoftware.smack.RosterEntry;

import com.ryannadams.cheeonk.client.chat.ClientBuddy;

// Wraps the RosterEntry object, but I hate that naming so I am changing it to buddy
public class BuddyWrapper extends Transmitted
{
	private final RosterEntry buddy;

	public BuddyWrapper(RosterEntry buddy)
	{
		super();

		this.buddy = buddy;
	}

	// TODO: don't do this, actually wrap the buddy object
	public RosterEntry getBuddy()
	{
		return buddy;
	}

	public ClientBuddy getClientBuddy()
	{
		return new ClientBuddy(buddy.getName(), buddy.getUser());
	}

}
