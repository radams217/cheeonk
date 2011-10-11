package com.cheeonk.client.handler;

import com.cheeonk.shared.event.AddBuddyEvent;
import com.cheeonk.shared.event.PresenceChangeEvent;
import com.cheeonk.shared.event.RemoveBuddyEvent;
import com.cheeonk.shared.event.UpdateBuddyEvent;
import com.google.gwt.event.shared.EventHandler;

// May only need one event object for these methods
public interface BuddyEventHandler extends EventHandler
{
	void onAddBuddy(AddBuddyEvent event);

	void onRemoveBuddy(RemoveBuddyEvent event);

	void onUpdateBuddy(UpdateBuddyEvent event);

	void onPresenceChange(PresenceChangeEvent event);

}
