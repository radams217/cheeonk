package com.ryannadams.cheeonk.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.ryannadams.cheeonk.client.handler.BuddyListEventHandler;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.buddy.CheeonkBuddy;

public class AddBuddyEvent extends GwtEvent<BuddyListEventHandler>
{
	public static final GwtEvent.Type<BuddyListEventHandler> TYPE = new GwtEvent.Type<BuddyListEventHandler>();

	private CheeonkBuddy buddy;
	private ConnectionKey key;

	public AddBuddyEvent(ConnectionKey key, CheeonkBuddy buddy)
	{
		this.key = key;
		this.buddy = buddy;
	}

	public ConnectionKey getConnectionKey()
	{
		return key;
	}

	public CheeonkBuddy getBuddy()
	{
		return buddy;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<BuddyListEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(BuddyListEventHandler handler)
	{
		handler.onAddBuddy(this);
	}

}
