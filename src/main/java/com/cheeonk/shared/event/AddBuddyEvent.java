package com.cheeonk.shared.event;

import com.cheeonk.client.handler.BuddyEventHandler;
import com.cheeonk.shared.buddy.IBuddy;
import com.google.gwt.event.shared.GwtEvent;

public class AddBuddyEvent extends GwtEvent<BuddyEventHandler> implements SharedEvent
{
	public static final GwtEvent.Type<BuddyEventHandler> TYPE = new GwtEvent.Type<BuddyEventHandler>();

	private IBuddy buddy;

	public AddBuddyEvent()
	{

	}

	public AddBuddyEvent(IBuddy buddy)
	{
		this.buddy = buddy;
	}

	public IBuddy getBuddy()
	{
		return buddy;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<BuddyEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(BuddyEventHandler handler)
	{
		handler.onAddBuddy(this);
	}

}
