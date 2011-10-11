package com.cheeonk.shared.event;

import com.cheeonk.client.handler.BuddyEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RemoveBuddyEvent extends GwtEvent<BuddyEventHandler>
{
	public static final GwtEvent.Type<BuddyEventHandler> TYPE = new GwtEvent.Type<BuddyEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<BuddyEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(BuddyEventHandler handler)
	{
		handler.onRemoveBuddy(this);
	}

}
