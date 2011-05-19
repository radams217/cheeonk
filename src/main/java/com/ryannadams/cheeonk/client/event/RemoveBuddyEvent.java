package com.ryannadams.cheeonk.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.ryannadams.cheeonk.client.handler.BuddyListEventHandler;

public class RemoveBuddyEvent extends GwtEvent<BuddyListEventHandler>
{
	public static final GwtEvent.Type<BuddyListEventHandler> TYPE = new GwtEvent.Type<BuddyListEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<BuddyListEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(BuddyListEventHandler handler)
	{
		handler.onRemoveBuddy(this);
	}

}
