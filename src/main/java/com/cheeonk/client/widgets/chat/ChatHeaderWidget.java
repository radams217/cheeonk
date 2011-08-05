package com.cheeonk.client.widgets.chat;

import com.cheeonk.client.ImageResources;
import com.cheeonk.shared.buddy.JabberId;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;

public class ChatHeaderWidget extends Composite
{
	private final HorizontalPanel panel;

	private final PushButton minimizeButton;
	private final PushButton maximizeButton;
	private final PushButton closeButton;

	public ChatHeaderWidget(JabberId jabberId)
	{
		panel = new HorizontalPanel();
		panel.add(new HTML(jabberId.toString()));

		minimizeButton = new PushButton(new Image(ImageResources.INSTANCE.getMinimizeSquare()));
		minimizeButton.setStylePrimaryName("nostyle");
		maximizeButton = new PushButton(new Image(ImageResources.INSTANCE.getMaximizeSquare()));
		maximizeButton.setStylePrimaryName("nostyle");
		closeButton = new PushButton(new Image(ImageResources.INSTANCE.getCloseSquare()));
		closeButton.setStylePrimaryName("nostyle");

		panel.add(minimizeButton);
		panel.add(maximizeButton);
		panel.add(closeButton);

		initWidget(panel);
		setStyleName("chatHeaderWidget");
	}

	public void addMinimizeClickHandler(ClickHandler handler)
	{
		minimizeButton.addClickHandler(handler);
	}

	public void addMaximizeClickHandler(ClickHandler handler)
	{
		maximizeButton.addClickHandler(handler);
	}

	public void addCloseClickHandler(ClickHandler handler)
	{
		closeButton.addClickHandler(handler);
	}
}
