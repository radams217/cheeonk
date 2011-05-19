package com.ryannadams.cheeonk.client.handler;

import com.google.gwt.event.shared.EventHandler;
import com.ryannadams.cheeonk.client.event.MessageReceivedEvent;
import com.ryannadams.cheeonk.client.event.MessageSentEvent;

public interface MessageEventHandler extends EventHandler
{
	void onMessageReceived(MessageReceivedEvent event);

	void onMessageSent(MessageSentEvent event);
}
