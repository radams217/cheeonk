package com.cheeonk.client.widgets.chat;

import com.cheeonk.client.ImageResources;
import com.cheeonk.shared.buddy.IBuddy;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;

public abstract class ChatHeaderWidget extends Composite
{
	private final HorizontalPanel panel;

	private final PushButton minimizeButton;
	private final PushButton maximizeButton;
	private final PushButton closeButton;

	public ChatHeaderWidget(IBuddy buddy)
	{
		panel = new HorizontalPanel();

		HTML buddyName = new HTML(buddy.getName());
		buddyName.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		panel.add(buddyName);

		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setStyleName("chatHeaderWidget-buttonPanel");
		minimizeButton = new PushButton(new Image(ImageResources.INSTANCE.getMinimizeSquare()), new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				onMinimize();
			}
		});
		minimizeButton.setStylePrimaryName("chatHeaderWidget-headerButton");
		maximizeButton = new PushButton(new Image(ImageResources.INSTANCE.getMaximizeSquare()), new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				onMaximize();
			}
		});
		maximizeButton.setStylePrimaryName("chatHeaderWidget-headerButton");
		closeButton = new PushButton(new Image(ImageResources.INSTANCE.getCloseSquare()), new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				onClose();
			}
		});
		closeButton.setStylePrimaryName("chatHeaderWidget-headerButton");

		buttonPanel.add(minimizeButton);
		buttonPanel.add(maximizeButton);
		buttonPanel.add(closeButton);
		panel.add(buttonPanel);

		initWidget(panel);
		setStyleName("chatHeaderWidget");
	}

	public abstract void onMinimize();

	public abstract void onMaximize();

	public abstract void onClose();
}
