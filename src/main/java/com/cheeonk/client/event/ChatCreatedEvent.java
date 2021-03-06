package com.cheeonk.client.event;

import com.cheeonk.client.handler.ChatEventHandler;
import com.cheeonk.shared.buddy.IBuddy;
import com.google.gwt.event.shared.GwtEvent;

public class ChatCreatedEvent extends GwtEvent<ChatEventHandler>
{
	public static final GwtEvent.Type<ChatEventHandler> TYPE = new GwtEvent.Type<ChatEventHandler>();

	private final IBuddy buddy;

	public ChatCreatedEvent(IBuddy buddy)
	{
		this.buddy = buddy;
	}

	public IBuddy getBuddy()
	{
		return buddy;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChatEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(ChatEventHandler handler)
	{
		handler.onChatCreated(this);
	}

}
