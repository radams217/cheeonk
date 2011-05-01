package com.ryannadams.cheeonk.server.internal.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

import com.ryannadams.cheeonk.server.internal.wrapper.BuddyWrapper;
import com.ryannadams.cheeonk.shared.buddy.ClientBuddy;

public class BuddyContainer implements RosterListener
{
	private final Set<BuddyWrapper> buddySet;

	public BuddyContainer()
	{
		buddySet = new HashSet<BuddyWrapper>();
	}

	public void addBuddy(RosterEntry rosterEntry, Presence presence)
	{
		buddySet.add(new BuddyWrapper(rosterEntry, presence));
	}

	public ClientBuddy[] getBuddyList()
	{
		List<ClientBuddy> buddyList = new ArrayList<ClientBuddy>();

		for (BuddyWrapper buddy : buddySet)
		{
			// if (!buddy.isTransmitted())
			// {
			buddyList.add(buddy.getClientBuddy());
			// buddy.setTransmitted(true);
			// }
		}

		return buddyList.toArray(new ClientBuddy[buddyList.size()]);
	}

	@Override
	public void entriesAdded(Collection<String> entries)
	{
		for (String entry : entries)
		{
			System.out.println(entry + ": add");
		}

	}

	@Override
	public void entriesDeleted(Collection<String> entries)
	{
		for (String entry : entries)
		{
			System.out.println(entry + ": deleted");
		}

	}

	@Override
	public void entriesUpdated(Collection<String> entries)
	{
		for (String entry : entries)
		{
			System.out.println(entry + ": update");
		}

	}

	@Override
	public void presenceChanged(Presence presence)
	{
		System.out.println("TO " + presence.getTo() + ": " + presence.getFrom() + " is " + presence.isAvailable());

		ClientBuddy buddy = new ClientBuddy();

	}

}