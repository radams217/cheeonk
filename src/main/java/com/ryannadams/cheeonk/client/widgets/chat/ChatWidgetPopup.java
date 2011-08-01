package com.ryannadams.cheeonk.client.widgets.chat;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.event.SignedinEvent;
import com.ryannadams.cheeonk.client.event.SignedoutEvent;
import com.ryannadams.cheeonk.client.handler.AuthenticationEventHandler;
import com.ryannadams.cheeonk.shared.buddy.JabberId;
import com.ryannadams.cheeonk.shared.message.IMessage;

public class ChatWidgetPopup extends PopupPanel implements AuthenticationEventHandler, IChatWidget
{
	private final ChatWidget chatWidget;

	public ChatWidgetPopup(SimpleEventBus eventBus, JabberId jabberId)
	{
		super(false);

		eventBus.addHandler(SignedoutEvent.TYPE, this);

		chatWidget = new ChatWidget(eventBus, jabberId);

		VerticalPanel panel = new VerticalPanel();
		panel.add(chatWidget);

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

	@Override
	public void addCheeonk(IMessage message)
	{
		chatWidget.addCheeonk(message);
	}
}
