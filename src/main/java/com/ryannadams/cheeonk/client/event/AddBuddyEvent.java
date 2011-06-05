package com.ryannadams.cheeonk.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.ryannadams.cheeonk.client.handler.BuddyListEventHandler;
import com.ryannadams.cheeonk.shared.buddy.CheeonkBuddy;

public class AddBuddyEvent extends GwtEvent<BuddyListEventHandler>
{
	public static final GwtEvent.Type<BuddyListEventHandler> TYPE = new GwtEvent.Type<BuddyListEventHandler>();

	private CheeonkBuddy buddy;

	public AddBuddyEvent(CheeonkBuddy buddy)
	{
		this.buddy = buddy;
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
