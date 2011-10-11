package com.cheeonk.client.handler;

import com.cheeonk.shared.event.SubscribeEvent;
import com.cheeonk.shared.event.UnSubscribeEvent;
import com.google.gwt.event.shared.EventHandler;

public interface SubscriptionEventHandler extends EventHandler
{
	void onSubscribe(SubscribeEvent event);

	void onUnSubscribe(UnSubscribeEvent event);
}
