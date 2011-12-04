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
import com.cheeonk.shared.buddy.IBuddy;
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
		final IBuddy buddy = event.getBuddy();

		if (!chats.containsKey(buddy.getJabberId()))
		{
			ChatPopupWidget popup = getChatPopupWidget(buddy.getJabberId());
			chats.put(buddy.getJabberId(), popup);
			panel.add(popup);
		}

		ChatPopupWidget chatWidget = (ChatPopupWidget) chats.get(buddy.getJabberId());

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
			ChatPopupWidget popup = getChatPopupWidget(key);
			chats.put(key, popup);
			panel.add(popup);
		}

		ChatPopupWidget chatWidget = (ChatPopupWidget) chats.get(key);

		if (chatWidget.isClosed())
		{
			panel.add(chatWidget);
		}

		chatWidget.maximize();
		chatWidget.addMessage(event.getMessage());

	}

	private ChatPopupWidget getChatPopupWidget(final JabberId key)
	{
		if (!chats.containsKey(key))
		{
			return new ChatPopupWidget(eventBus)
			{
				@Override
				public void close()
				{
					super.close();

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

				@Override
				public IBuddy getParticipant()
				{
					return new CheeonkBuddy(key, key.getJabberId());
				}
			};
		}

		return (ChatPopupWidget) chats.get(key);
	}

	@Override
	public void onMessageSent(MessageSentEvent event)
	{
		// TODO Auto-generated method stub

	}

}
