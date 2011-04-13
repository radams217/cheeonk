package com.ryannadams.cheeonk.server.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

import com.ryannadams.cheeonk.client.chat.ClientBuddy;

public class BuddyContainer implements RosterListener
{
	private final Set<BuddyWrapper> buddySet;

	public BuddyContainer()
	{
		buddySet = new HashSet<BuddyWrapper>();
	}

	public void addBuddy(RosterEntry rosterEntry)
	{
		buddySet.add(new BuddyWrapper(rosterEntry));
	}

	public ClientBuddy[] getBuddyList()
	{
		List<ClientBuddy> buddyList = new ArrayList<ClientBuddy>();

		for (BuddyWrapper buddy : buddySet)
		{
			if (!buddy.isTransmitted())
			{
				buddyList.add(buddy.getClientBuddy());
				buddy.setTransmitted(true);
			}
		}

		return buddyList.toArray(new ClientBuddy[buddyList.size()]);
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
