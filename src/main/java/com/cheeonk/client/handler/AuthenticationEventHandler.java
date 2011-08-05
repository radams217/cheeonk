package com.cheeonk.client.handler;

import com.cheeonk.client.event.SignedinEvent;
import com.cheeonk.client.event.SignedoutEvent;
import com.google.gwt.event.shared.EventHandler;

public interface AuthenticationEventHandler extends EventHandler
{
	void onSignedin(SignedinEvent event);

	void onSignedout(SignedoutEvent event);
}
