package com.ryannadams.cheeonk.server.internal.containers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

import com.ryannadams.cheeonk.server.internal.wrappers.BuddyWrapper;
import com.ryannadams.cheeonk.shared.buddy.ClientBuddy;

public class BuddyContainer implements RosterListener
{
	private final Map<String, BuddyWrapper> buddyMap;

	public BuddyContainer()
	{
		buddyMap = new HashMap<String, BuddyWrapper>();
	}

	public void addBuddy(RosterEntry rosterEntry, Presence presence)
	{
		buddyMap.put(rosterEntry.getUser(), new BuddyWrapper(rosterEntry, presence));
	}

	public ClientBuddy[] getBuddyList()
	{
		List<ClientBuddy> buddyList = new ArrayList<ClientBuddy>();

		for (BuddyWrapper buddy : buddyMap.values())
		{
			buddyList.add(buddy.getClientBuddy());
			buddy.setTransmitted(true);
		}

		return buddyList.toArray(new ClientBuddy[buddyList.size()]);
	}

	public ClientBuddy[] getBuddyListUpdates()
	{
		List<ClientBuddy> buddyList = new ArrayList<ClientBuddy>();

		for (BuddyWrapper buddy : buddyMap.values())
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

		String jID = presence.getFrom().substring(0, presence.getFrom().indexOf('/'));

		BuddyWrapper buddy = buddyMap.get(jID);
		buddy.setPresence(presence);
		buddy.setTransmitted(false);
	}

}
