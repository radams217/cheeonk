package com.ryannadams.cheeonk.client.widgets.chat;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.ryannadams.cheeonk.client.ImageResources;
import com.ryannadams.cheeonk.shared.buddy.JabberId;
import com.ryannadams.cheeonk.shared.message.IMessage;

public class ChatPanelPlaceHolder extends Composite implements HasClickHandlers, IChatWidget, ClickHandler
{
	private final ChatWidgetPopup chatPopup;
	private final HorizontalPanel panel;

	public ChatPanelPlaceHolder(SimpleEventBus eventBus, JabberId jabberId)
	{
		chatPopup = new ChatWidgetPopup(eventBus, jabberId);

		panel = new HorizontalPanel();
		panel.add(new HTML(jabberId.toString()));

		PushButton minimizeButton = new PushButton(new Image(ImageResources.INSTANCE.getMinimizeSquare()), new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				hide();
			}
		});

		minimizeButton.setStylePrimaryName("nostyle");

		PushButton maximizeButton = new PushButton(new Image(ImageResources.INSTANCE.getMaximizeSquare()), new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				show();
			}
		});

		maximizeButton.setStylePrimaryName("nostyle");
		PushButton closeButton = new PushButton(new Image(ImageResources.INSTANCE.getCloseSquare()), this);
		closeButton.setStylePrimaryName("nostyle");

		panel.add(minimizeButton);
		panel.add(maximizeButton);
		panel.add(closeButton);

		initWidget(panel);
		setStyleName("chatHolder");
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler)
	{
		return addDomHandler(handler, ClickEvent.getType());
	}

	@Override
	public void show()
	{
		chatPopup.setPopupPositionAndShow(new PopupPanel.PositionCallback()
		{
			@Override
			public void setPosition(int offsetWidth, int offsetHeight)
			{
				int left = panel.getAbsoluteLeft();
				int top = panel.getAbsoluteTop() - chatPopup.getOffsetHeight();
				chatPopup.setPopupPosition(left, top);
			}
		});
	}

	@Override
	public void hide()
	{
		chatPopup.hide();
	}

	@Override
	public void addCheeonk(IMessage message)
	{
		chatPopup.addCheeonk(message);
	}

	@Override
	public void onClick(ClickEvent event)
	{

	}

}
