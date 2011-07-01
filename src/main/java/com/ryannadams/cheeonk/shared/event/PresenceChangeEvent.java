package com.ryannadams.cheeonk.shared.event;

import com.google.gwt.event.shared.GwtEvent;
import com.ryannadams.cheeonk.client.handler.BuddyEventHandler;
import com.ryannadams.cheeonk.shared.buddy.IBuddy;

public class PresenceChangeEvent extends GwtEvent<BuddyEventHandler> implements IEvent
{
	public static final GwtEvent.Type<BuddyEventHandler> TYPE = new GwtEvent.Type<BuddyEventHandler>();

	private IBuddy buddy;

	public PresenceChangeEvent()
	{

	}

	public PresenceChangeEvent(IBuddy buddy)
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
		handler.onPresenceChange(this);
	}

}
