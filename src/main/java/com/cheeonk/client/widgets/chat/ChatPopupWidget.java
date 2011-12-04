package com.cheeonk.client.widgets.chat;

import com.cheeonk.shared.buddy.IBuddy;
import com.cheeonk.shared.message.IMessage;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class ChatPopupWidget extends AbstractChatWidget
{
	private final SimplePanel panel;
	private final PopupPanel popupPanel;

	private final ChatWidget chatWidget;

	public ChatPopupWidget(SimpleEventBus eventBus)
	{
		panel = new SimplePanel();
		panel.setStyleName("chatPopupWidget");

		popupPanel = new PopupPanel();
		popupPanel.setStyleName("chatPopupWidget-popupPanel");

		chatWidget = new ChatWidget(eventBus)
		{
			@Override
			public void minimize()
			{
				super.minimize();

				panel.clear();
				panel.add(this);
				popupPanel.hide();

			}

			@Override
			public void maximize()
			{
				super.maximize();

				panel.clear();

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

			}

			@Override
			public void close()
			{
				super.close();

				popupPanel.hide();
			}

			@Override
			public IBuddy getParticipant()
			{
				return ChatPopupWidget.this.getParticipant();
			}
		};

		final VerticalPanel popupContents = new VerticalPanel();
		popupContents.setStyleName("chatPopupWidget-popupContents");
		popupContents.add(chatWidget);

		popupPanel.add(popupContents);

		initWidget(panel);
	}

	@Override
	public void minimize()
	{
		chatWidget.minimize();
	}

	@Override
	public void maximize()
	{
		chatWidget.maximize();
	}

	@Override
	public void close()
	{
		chatWidget.close();
	}

	@Override
	public abstract IBuddy getParticipant();

	@Override
	public void addMessage(IMessage message)
	{
		chatWidget.addMessage(message);
	}
}