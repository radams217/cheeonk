package com.cheeonk.client.widgets.chat;

import com.cheeonk.shared.buddy.JabberId;
import com.cheeonk.shared.message.IMessage;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ChatPopupWidget extends Composite implements IChatWidget
{
	private final ChatWidget chatWidget;
	private final ChatHeaderWidget headerWidget;
	private final SimplePanel blankPanel;
	private final HorizontalPanel panel;

	public ChatPopupWidget(SimpleEventBus eventBus, JabberId jabberId)
	{
		panel = new HorizontalPanel();
		headerWidget = new ChatHeaderWidget(jabberId);
		blankPanel = new SimplePanel();
		blankPanel.setStyleName("blankPanel");
		chatWidget = new ChatWidget(eventBus, jabberId);

		final PopupPanel popupPanel = new PopupPanel();
		popupPanel.setStyleName("chatPopupWidget");

		final VerticalPanel popupContents = new VerticalPanel();
		popupContents.add(chatWidget);
		popupPanel.add(popupContents);

		headerWidget.addMinimizeClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				popupPanel.hide();
				onMinimize();
				panel.remove(blankPanel);
				panel.add(headerWidget);
			}
		});

		headerWidget.addMaximizeClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				popupContents.insert(headerWidget, 0);
				panel.add(blankPanel);
				popupPanel.setPopupPositionAndShow(new PopupPanel.PositionCallback()
				{
					@Override
					public void setPosition(int offsetWidth, int offsetHeight)
					{
						int left = panel.getAbsoluteLeft();
						int top = panel.getAbsoluteTop() - popupPanel.getOffsetHeight();
						popupPanel.setPopupPosition(left, top);
					}
				});

				onMaximize();
			}
		});

		headerWidget.addCloseClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				popupPanel.hide();
				onClose();
			}
		});

		panel.add(headerWidget);
		initWidget(panel);
		setStyleName("chatHolder");
	}

	@Override
	public void onMinimize()
	{

	}

	@Override
	public void onMaximize()
	{

	}

	@Override
	public void onClose()
	{

	}

	@Override
	public void addCheeonk(IMessage message)
	{
		chatWidget.addCheeonk(message);
	}

}
