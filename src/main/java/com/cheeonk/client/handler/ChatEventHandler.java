package com.cheeonk.client.handler;

import com.cheeonk.client.event.ChatCreatedEvent;
import com.google.gwt.event.shared.EventHandler;

public interface ChatEventHandler extends EventHandler
{
	void onChatCreated(ChatCreatedEvent event);
}
