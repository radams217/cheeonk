package com.ryannadams.cheeonk.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.ryannadams.cheeonk.client.handler.ChatEventHandler;
import com.ryannadams.cheeonk.shared.chat.CheeonkChat;

public class ChatCreatedEvent extends GwtEvent<ChatEventHandler>
{
	public static final GwtEvent.Type<ChatEventHandler> TYPE = new GwtEvent.Type<ChatEventHandler>();

	private CheeonkChat chat;

	public ChatCreatedEvent(CheeonkChat chat)
	{
		this.chat = chat;
	}

	public CheeonkChat getChat()
	{
		return chat;
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
