package com.ryannadams.cheeonk.client.handler;

import com.google.gwt.event.shared.EventHandler;
import com.ryannadams.cheeonk.client.event.MessageSentEvent;
import com.ryannadams.cheeonk.shared.event.MessageReceivedEvent;

public interface MessageEventHandler extends EventHandler
{
	void onMessageReceived(MessageReceivedEvent event);

	void onMessageSent(MessageSentEvent event);
}
