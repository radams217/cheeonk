package com.ryannadams.cheeonk.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.ryannadams.cheeonk.client.handler.AuthenticationEventHandler;
import com.ryannadams.cheeonk.shared.buddy.JabberId;

public class SignedinEvent extends GwtEvent<AuthenticationEventHandler>
{
	public static final GwtEvent.Type<AuthenticationEventHandler> TYPE = new GwtEvent.Type<AuthenticationEventHandler>();

	private String connectionId;
	private JabberId jabberId;

	public SignedinEvent(String connectionId, JabberId jabberId)
	{
		this.connectionId = connectionId;
		this.jabberId = jabberId;
	}

	public String getConnectionId()
	{
		return connectionId;
	}

	public JabberId getJabberId()
	{
		return jabberId;
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
