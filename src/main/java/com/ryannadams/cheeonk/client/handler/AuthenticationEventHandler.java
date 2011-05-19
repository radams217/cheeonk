package com.ryannadams.cheeonk.client.handler;

import com.google.gwt.event.shared.EventHandler;
import com.ryannadams.cheeonk.client.event.SignedinEvent;
import com.ryannadams.cheeonk.client.event.SignedoutEvent;

public interface AuthenticationEventHandler extends EventHandler
{
	void onSignedin(SignedinEvent event);

	void onSignedout(SignedoutEvent event);
}
