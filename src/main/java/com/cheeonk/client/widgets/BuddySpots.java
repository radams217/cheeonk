package com.cheeonk.client.widgets;

import com.cheeonk.client.handler.BuddyEventHandler;
import com.cheeonk.shared.event.AddBuddyEvent;
import com.cheeonk.shared.event.PresenceChangeEvent;
import com.cheeonk.shared.event.RemoveBuddyEvent;
import com.cheeonk.shared.event.UpdateBuddyEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class BuddySpots extends Composite implements BuddyEventHandler
{
	private final SimpleEventBus eventBus;

	private FlowPanel panel;

	public BuddySpots(final SimpleEventBus eventBus)
	{
		this.eventBus = eventBus;
		// this.eventBus.addHandler(SignedinEvent.TYPE, this);
		// this.eventBus.addHandler(SignedoutEvent.TYPE, this);
		this.eventBus.addHandler(AddBuddyEvent.TYPE, this);
		this.eventBus.addHandler(RemoveBuddyEvent.TYPE, this);
		// this.eventBus.addHandler(SubscribeEvent.TYPE, this);
		// this.eventBus.addHandler(UnSubscribeEvent.TYPE, this);

		panel = new FlowPanel();

		initWidget(panel);
	}

	@Override
	public void onAddBuddy(AddBuddyEvent event)
	{
		panel.add(new BuddySpot(eventBus, event.getBuddy()));
	}

	@Override
	public void onRemoveBuddy(RemoveBuddyEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpdateBuddy(UpdateBuddyEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onPresenceChange(PresenceChangeEvent event)
	{
		// TODO Auto-generated method stub

	}

}
