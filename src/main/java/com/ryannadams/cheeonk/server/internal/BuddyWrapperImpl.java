package com.ryannadams.cheeonk.server.internal;

import org.jivesoftware.smack.RosterEntry;

import com.ryannadams.cheeonk.client.chat.IBuddy;

public class BuddyWrapperImpl implements IBuddy
{
	private RosterEntry buddy;

	public BuddyWrapperImpl(RosterEntry buddy)
	{
		this.buddy = buddy;
	}

	@Override
	public String getName()
	{
		return buddy.getName();
	}

	@Override
	public String getUser()
	{
		return buddy.getUser();
	}

}
