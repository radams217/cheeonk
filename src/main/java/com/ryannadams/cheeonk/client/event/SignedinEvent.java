package com.ryannadams.cheeonk.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.ryannadams.cheeonk.client.handler.AuthenticationEventHandler;
import com.ryannadams.cheeonk.shared.ConnectionKey;

public class SignedinEvent extends GwtEvent<AuthenticationEventHandler>
{
	public static final GwtEvent.Type<AuthenticationEventHandler> TYPE = new GwtEvent.Type<AuthenticationEventHandler>();

	private ConnectionKey key;

	public SignedinEvent(ConnectionKey key)
	{
		this.key = key;
	}

	public ConnectionKey getConnectionKey()
	{
		return key;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AuthenticationEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(AuthenticationEventHandler handler)
	{
		handler.onSignedin(this);
	}

}
