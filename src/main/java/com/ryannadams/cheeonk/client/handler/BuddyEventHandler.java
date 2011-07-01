package com.ryannadams.cheeonk.client.handler;

import com.google.gwt.event.shared.EventHandler;
import com.ryannadams.cheeonk.client.event.AddBuddyEvent;
import com.ryannadams.cheeonk.client.event.PresenceChangeEvent;
import com.ryannadams.cheeonk.client.event.RemoveBuddyEvent;

public interface BuddyEventHandler extends EventHandler
{
	void onAddBuddy(AddBuddyEvent event);

	void onRemoveBuddy(RemoveBuddyEvent event);

	void onPresenceChange(PresenceChangeEvent event);
}
