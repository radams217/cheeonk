package com.ryannadams.cheeonk.shared.event;

import com.google.gwt.event.shared.GwtEvent;
import com.ryannadams.cheeonk.client.handler.MessageEventHandler;
import com.ryannadams.cheeonk.shared.message.IMessage;

public class MessageReceivedEvent extends GwtEvent<MessageEventHandler> implements SharedEvent
{
	public static final GwtEvent.Type<MessageEventHandler> TYPE = new GwtEvent.Type<MessageEventHandler>();

	private IMessage message;

	@Deprecated
	public MessageReceivedEvent()
	{

	}

	public MessageReceivedEvent(IMessage message)
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
		handler.onMessageReceived(this);
	}

}
