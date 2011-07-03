package com.ryannadams.cheeonk.client.handler;

import com.google.gwt.event.shared.EventHandler;
import com.ryannadams.cheeonk.client.event.ChatCreatedEvent;

public interface ChatEventHandler extends EventHandler
{
	void onChatCreated(ChatCreatedEvent event);
}
