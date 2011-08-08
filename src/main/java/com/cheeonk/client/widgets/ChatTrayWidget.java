package com.cheeonk.client.widgets;

import java.util.HashMap;
import java.util.Map;

import com.cheeonk.client.event.ChatCreatedEvent;
import com.cheeonk.client.event.MessageSentEvent;
import com.cheeonk.client.handler.ChatEventHandler;
import com.cheeonk.client.handler.MessageEventHandler;
import com.cheeonk.client.widgets.chat.ChatPopupWidget;
import com.cheeonk.client.widgets.chat.IChatWidget;
import com.cheeonk.shared.buddy.CheeonkBuddy;
import com.cheeonk.shared.buddy.JabberId;
import com.cheeonk.shared.event.MessageReceivedEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ChatTrayWidget extends Composite implements ChatEventHandler, MessageEventHandler
{
	private final HorizontalPanel panel;
	private final Map<JabberId, IChatWidget> chats;

	private final SimpleEventBus eventBus;

	public ChatTrayWidget(SimpleEventBus eventBus)
	{
		this.panel = new HorizontalPanel();
		this.panel.setStyleName("chatTrayWidget");

		this.eventBus = eventBus;
		this.eventBus.addHandler(ChatCreatedEvent.TYPE, this);
		this.eventBus.addHandler(MessageReceivedEvent.TYPE, this);

		this.chats = new HashMap<JabberId, IChatWidget>();

		initWidget(panel);
	}

	public IChatWidget get(JabberId key)
	{
		return chats.get(key);
	}

	public void clear()
	{
		for (IChatWidget chatWidget : chats.values())
		{
			chatWidget.close();
		}

		chats.clear();
	}

	@Override
	public void onChatCreated(ChatCreatedEvent event)
	{
		final JabberId key = event.getBuddy().getJabberId();

		if (!chats.containsKey(key))
		{
			ChatPopupWidget chatPopupWidget = new ChatPopupWidget(eventBus, event.getBuddy())
			{
				@Override
				public void onClose()
				{
					panel.remove(this);

					for (IChatWidget chatWidget : chats.values())
					{
						// Redraw the popups just in case their position on the
						// panel changes
						if (chatWidget.isMaximized())
						{
							chatWidget.maximize();
						}
					}
				}
			};

			chats.put(key, chatPopupWidget);
			panel.add(chatPopupWidget);
		}

		ChatPopupWidget chatWidget = (ChatPopupWidget) chats.get(key);

		if (chatWidget.isClosed())
		{
			panel.add(chatWidget);
		}

		chatWidget.maximize();
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		JabberId key = event.getMessage().getFrom();

		if (!chats.containsKey(key))
		{
			ChatPopupWidget chatPopupWidget = new ChatPopupWidget(eventBus, new CheeonkBuddy(key, "test"))
			{
				@Override
				public void onClose()
				{
					panel.remove(this);

					for (IChatWidget chatWidget : chats.values())
					{
						// Redraw the popups just in case their position on the
						// panel changes
						if (chatWidget.isMaximized())
						{
							chatWidget.maximize();
						}
					}
				}
			};

			chats.put(key, chatPopupWidget);
			panel.add(chatPopupWidget);
		}

		ChatPopupWidget chatWidget = (ChatPopupWidget) chats.get(key);

		if (chatWidget.isClosed())
		{
			panel.add(chatWidget);
		}

		chatWidget.maximize();
		chatWidget.addCheeonk(event.getMessage());

	}

	@Override
	public void onMessageSent(MessageSentEvent event)
	{
		// TODO Auto-generated method stub

	}

}
