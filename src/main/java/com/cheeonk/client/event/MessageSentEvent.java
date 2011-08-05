package com.cheeonk.client.event;

import com.cheeonk.client.handler.MessageEventHandler;
import com.cheeonk.shared.message.IMessage;
import com.google.gwt.event.shared.GwtEvent;

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
