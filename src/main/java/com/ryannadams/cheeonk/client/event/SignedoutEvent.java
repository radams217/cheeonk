package com.ryannadams.cheeonk.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.ryannadams.cheeonk.client.handler.AuthenticationEventHandler;

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
