package com.ryannadams.cheeonk.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.ryannadams.cheeonk.client.chat.ClientBuddy;

public class BuddyWidget extends Composite implements ClickHandler
{
	private final ClientBuddy buddy;

	public BuddyWidget(ClientBuddy buddy)
	{
		Button button = new Button(buddy.getName());

		button.addClickHandler(this);

		this.buddy = buddy;

		HorizontalPanel panel = new HorizontalPanel();

		panel.add(button);
		initWidget(panel);
	}

	@Override
	public void onClick(ClickEvent event)
	{
		// TODO Auto-generated method stub

	}

}
