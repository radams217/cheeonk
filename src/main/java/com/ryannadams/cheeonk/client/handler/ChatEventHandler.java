package com.ryannadams.cheeonk.client.handler;

import com.google.gwt.event.shared.EventHandler;
import com.ryannadams.cheeonk.client.event.ChatCreatedEvent;
import com.ryannadams.cheeonk.client.event.ChatIncomingEvent;

public interface ChatEventHandler extends EventHandler
{
	void onChatCreated(ChatCreatedEvent event);

	void onChatIncoming(ChatIncomingEvent event);
}
