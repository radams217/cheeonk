package com.cheeonk.client.handler;

import com.cheeonk.client.event.RemoveBuddyEvent;
import com.cheeonk.shared.event.AddBuddyEvent;
import com.cheeonk.shared.event.PresenceChangeEvent;
import com.google.gwt.event.shared.EventHandler;

public interface BuddyEventHandler extends EventHandler
{
	void onAddBuddy(AddBuddyEvent event);

	void onRemoveBuddy(RemoveBuddyEvent event);

	void onPresenceChange(PresenceChangeEvent event);
}
