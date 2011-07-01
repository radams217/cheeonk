package com.ryannadams.cheeonk.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.ryannadams.cheeonk.client.handler.BuddyEventHandler;

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
