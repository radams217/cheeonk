package com.cheeonk.client.widgets.chat;

import com.cheeonk.shared.buddy.IBuddy;
import com.cheeonk.shared.message.IMessage;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ChatPopupWidget extends AbstractChatWidget
{
	private final SimplePanel panel;
	private final ChatHeaderWidget headerWidget;
	private final ChatWidget chatWidget;

	public ChatPopupWidget(SimpleEventBus eventBus, IBuddy buddy)// JabberId
																	// jabberId)
	{
		panel = new SimplePanel();
		panel.setStyleName("chatPopupWidget");

		final PopupPanel popupPanel = new PopupPanel();
		popupPanel.setStyleName("chatPopupWidget-popupPanel");

		chatWidget = new ChatWidget(eventBus, buddy);

		final VerticalPanel popupContents = new VerticalPanel();
		popupContents.setStyleName("chatPopupWidget-popupContents");
		popupPanel.add(popupContents);

		headerWidget = new ChatHeaderWidget(buddy)
		{
			@Override
			public void onMinimize()
			{
				panel.clear();
				panel.add(this);
				popupPanel.hide();

				setMinimized(true);
				setMaximized(false);
				setClosed(false);
			}

			@Override
			public void onMaximize()
			{
				panel.clear();

				popupContents.clear();
				popupContents.add(this);
				popupContents.add(chatWidget);
				popupPanel.setPopupPositionAndShow(new PopupPanel.PositionCallback()
				{
					@Override
					public void setPosition(int offsetWidth, int offsetHeight)
					{
						int left = panel.getAbsoluteLeft();
						int top = panel.getAbsoluteTop() + panel.getOffsetHeight() - popupPanel.getOffsetHeight();
						popupPanel.setPopupPosition(left, top);
					}
				});

				setMinimized(false);
				setMaximized(true);
				setClosed(false);
			}

			@Override
			public void onClose()
			{
				popupPanel.hide();
				ChatPopupWidget.this.onClose();
				setMinimized(false);
				setMaximized(false);
				setClosed(true);
			}
		};

		panel.add(headerWidget);
		initWidget(panel);

		setMinimized(true);
		setMaximized(false);
		setClosed(false);
	}

	@Override
	public void addCheeonk(IMessage message)
	{
		chatWidget.addCheeonk(message);
	}

	@Override
	public void minimize()
	{
		headerWidget.onMinimize();
	}

	@Override
	public void maximize()
	{
		headerWidget.onMaximize();
	}

	@Override
	public void close()
	{
		headerWidget.onClose();
	}

	public void onClose()
	{

	}
}
