package com.cheeonk.client.event;

import com.cheeonk.client.handler.AuthenticationEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SignedoutEvent extends GwtEvent<AuthenticationEventHandler>
{
	public static final GwtEvent.Type<AuthenticationEventHandler> TYPE = new GwtEvent.Type<AuthenticationEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AuthenticationEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(AuthenticationEventHandler handler)
	{
		handler.onSignedout(this);
	}

}
