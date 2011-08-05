package com.cheeonk.client.handler;

import com.cheeonk.client.event.MessageSentEvent;
import com.cheeonk.shared.event.MessageReceivedEvent;
import com.google.gwt.event.shared.EventHandler;

public interface MessageEventHandler extends EventHandler
{
	void onMessageReceived(MessageReceivedEvent event);

	void onMessageSent(MessageSentEvent event);
}
