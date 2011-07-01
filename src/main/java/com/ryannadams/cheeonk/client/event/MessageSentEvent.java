package com.ryannadams.cheeonk.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.ryannadams.cheeonk.client.handler.MessageEventHandler;
import com.ryannadams.cheeonk.shared.message.IMessage;

public class MessageSentEvent extends GwtEvent<MessageEventHandler>
{
	public static final GwtEvent.Type<MessageEventHandler> TYPE = new GwtEvent.Type<MessageEventHandler>();

	private final IMessage message;

	public MessageSentEvent(IMessage message)
	{
		this.message = message;
	}

	public IMessage getMessage()
	{
		return message;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MessageEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(MessageEventHandler handler)
	{
		handler.onMessageSent(this);
	}

}
