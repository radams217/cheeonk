package com.ryannadams.cheeonk.server.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

public class BuddyContainer implements RosterListener
{
	private final Set<BuddyWrapper> buddySet = new HashSet<BuddyWrapper>();

	public BuddyContainer(Collection<RosterEntry> rosterEntries)
	{
		for (RosterEntry rosterEntry : rosterEntries)
		{
			buddySet.add(new BuddyWrapper(rosterEntry));
		}
	}

	public Set<BuddyWrapper> getBuddySet()
	{
		return buddySet;
	}

	@Override
	public void entriesAdded(Collection<String> arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void entriesDeleted(Collection<String> arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void entriesUpdated(Collection<String> arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void presenceChanged(Presence arg0)
	{
		// TODO Auto-generated method stub

	}

}
