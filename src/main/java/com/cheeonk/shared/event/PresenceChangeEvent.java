package com.cheeonk.shared.event;

import com.cheeonk.client.handler.BuddyEventHandler;
import com.cheeonk.shared.buddy.CheeonkPresence;
import com.google.gwt.event.shared.GwtEvent;

public class PresenceChangeEvent extends GwtEvent<BuddyEventHandler> implements SharedEvent
{
	public static final GwtEvent.Type<BuddyEventHandler> TYPE = new GwtEvent.Type<BuddyEventHandler>();

	private CheeonkPresence presence;

	@Deprecated
	public PresenceChangeEvent()
	{

	}

	public PresenceChangeEvent(CheeonkPresence presence)
	{
		this.presence = presence;
	}

	public CheeonkPresence getPresence()
	{
		return presence;
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
