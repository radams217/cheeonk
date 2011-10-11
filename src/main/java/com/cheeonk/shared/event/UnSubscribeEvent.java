package com.cheeonk.shared.event;

import com.cheeonk.client.handler.SubscriptionEventHandler;
import com.cheeonk.shared.buddy.IBuddy;
import com.google.gwt.event.shared.GwtEvent;

public class UnSubscribeEvent extends GwtEvent<SubscriptionEventHandler> implements SharedEvent
{
	public static final GwtEvent.Type<SubscriptionEventHandler> TYPE = new GwtEvent.Type<SubscriptionEventHandler>();

	private IBuddy buddy;

	public UnSubscribeEvent()
	{

	}

	public UnSubscribeEvent(IBuddy buddy)
	{
		this.buddy = buddy;
	}

	public IBuddy getBuddy()
	{
		return buddy;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<SubscriptionEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(SubscriptionEventHandler handler)
	{
		handler.onUnSubscribe(this);
	}

}
