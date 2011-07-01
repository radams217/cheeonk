package com.ryannadams.cheeonk.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.event.SignedinEvent;
import com.ryannadams.cheeonk.client.event.SignedoutEvent;
import com.ryannadams.cheeonk.client.handler.AuthenticationEventHandler;
import com.ryannadams.cheeonk.shared.buddy.IBuddy;

public class ChatWidgetDialog extends DialogBox implements AuthenticationEventHandler
{
	private final ChatWidget chatWidget;

	public ChatWidgetDialog(SimpleEventBus eventBus, IBuddy buddy)
	{
		super(false);

		setModal(false);

		eventBus.addHandler(SignedoutEvent.TYPE, this);

		chatWidget = new ChatWidget(eventBus, buddy);

		Button close = new Button("Close", new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				hide();
			}
		});

		VerticalPanel panel = new VerticalPanel();
		panel.add(chatWidget);
		panel.add(close);

		setWidget(panel);
	}

	@Override
	public void onSignedin(SignedinEvent event)
	{

	}

	@Override
	public void onSignedout(SignedoutEvent event)
	{
		hide();
	}

}
