package com.cheeonk.client.widgets;

import com.cheeonk.client.widgets.chat.ChatWidget;
import com.cheeonk.client.widgets.chat.IChatWidget;
import com.cheeonk.shared.buddy.IBuddy;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BuddySpot extends Composite
{
	private final IBuddy buddy;

	private final BuddyWidget buddyWidget;
	private final BuddyEditWidget buddyEditWidget;
	private final IChatWidget chatWidget;

	private SimplePanel panel;

	public BuddySpot(final SimpleEventBus eventBus, final IBuddy buddy)
	{
		this.buddy = buddy;

		this.panel = new SimplePanel();

		this.buddyWidget = new BuddyWidget(eventBus)
		{
			@Override
			public IBuddy getBuddy()
			{
				return buddy;
			}

		};

		this.buddyEditWidget = new BuddyEditWidget();
		this.chatWidget = new ChatWidget(eventBus)
		{
			@Override
			public IBuddy getParticipant()
			{
				return buddy;
			}
		};

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(buddyWidget);
		verticalPanel.add((ChatWidget) chatWidget);
		panel.add(verticalPanel);

		initWidget(panel);
	}

}
