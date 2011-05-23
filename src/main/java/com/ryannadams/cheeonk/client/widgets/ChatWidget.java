package com.ryannadams.cheeonk.client.widgets;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.callback.GotMessages;
import com.ryannadams.cheeonk.client.callback.SentMessage;
import com.ryannadams.cheeonk.client.event.ChatCreatedEvent;
import com.ryannadams.cheeonk.client.event.ChatReceivedEvent;
import com.ryannadams.cheeonk.client.event.MessageReceivedEvent;
import com.ryannadams.cheeonk.client.event.MessageSentEvent;
import com.ryannadams.cheeonk.client.event.SignedinEvent;
import com.ryannadams.cheeonk.client.event.SignedoutEvent;
import com.ryannadams.cheeonk.client.handler.AuthenticationEventHandler;
import com.ryannadams.cheeonk.client.handler.ChatEventHandler;
import com.ryannadams.cheeonk.client.handler.MessageEventHandler;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.GetMessages;
import com.ryannadams.cheeonk.shared.action.SendMessage;
import com.ryannadams.cheeonk.shared.chat.CheeonkChat;
import com.ryannadams.cheeonk.shared.message.CheeonkMessage;

/**
 * @author radams217
 * 
 *         The ChatWidget is essentially a chat window that a user can send and
 *         receive messages from other users. The timer is used to poll messages
 *         coming from the server. This widget can be added to a dialog box or
 *         any type of panel.
 */
public class ChatWidget extends Composite implements MessageEventHandler, ChatEventHandler, AuthenticationEventHandler
{
	private final VerticalPanel cheeonks;
	private final TextArea messageArea;
	private final ScrollPanel scrollPanel;

	private Timer timer;

	private final DispatchAsync dispatchAsync;

	public ChatWidget(SimpleEventBus eventBus)
	{
		eventBus.addHandler(MessageReceivedEvent.TYPE, this);
		eventBus.addHandler(MessageSentEvent.TYPE, this);
		eventBus.addHandler(ChatCreatedEvent.TYPE, this);
		eventBus.addHandler(ChatReceivedEvent.TYPE, this);
		eventBus.addHandler(SignedoutEvent.TYPE, this);

		dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

		cheeonks = new VerticalPanel();
		// scroll panel is set need to set the inner vertical pannel
		// cheeonks.addStyleName("cheeonkWidget-Cheeonks");
		scrollPanel = new ScrollPanel();
		scrollPanel.addStyleName("chatWidget-Cheeonks");
		scrollPanel.add(cheeonks);

		messageArea = new TextArea();
		messageArea.addStyleName("chatWidget-MessageArea");
		messageArea.addKeyPressHandler(new KeyPressHandler()
		{
			@Override
			public void onKeyPress(KeyPressEvent event)
			{
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				{
					addCheeonk("me", messageArea.getText());
				}
			}

		});

		VerticalPanel panel = new VerticalPanel();
		panel.addStyleName("chatWidget");
		panel.add(scrollPanel);
		panel.add(messageArea);

		initWidget(panel);
	}

	public void addKeyPressHandler(KeyPressHandler handler)
	{
		messageArea.addKeyPressHandler(handler);
	}

	public void addCheeonk(String sender, String message)
	{
		cheeonks.add(new Cheeonk(sender, message));
		scrollPanel.scrollToBottom();
	}

	public void resetMessageArea()
	{
		messageArea.setCursorPos(0);
		messageArea.setText("");
	}

	public CheeonkMessage getMessageAreaText()
	{
		return new CheeonkMessage(messageArea.getText(), "", "");
	}

	public void setTimer(Timer timer, int periodMillis)
	{
		this.timer = timer;
		this.timer.scheduleRepeating(periodMillis);
	}

	public void cancelTimer()
	{
		timer.cancel();
	}

	private class Cheeonk extends Composite implements ClickHandler
	{
		private final HTML cheeonk;
		private final PushButton cheeonkCastButton;

		private final String sender;
		private final String message;

		public Cheeonk(String sender, String message)
		{
			this.sender = sender;
			this.message = message;

			cheeonk = new HTML(sender + ": " + message);
			cheeonkCastButton = new PushButton("C", this);

			HorizontalPanel panel = new HorizontalPanel();
			panel.add(cheeonk);
			panel.add(cheeonkCastButton);

			initWidget(panel);
		}

		@Override
		public void onClick(ClickEvent event)
		{
			// TODO Auto-generated method stub

		}

	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		addCheeonk(event.getMessage().getTo(), event.getMessage().getBody());
	}

	@Override
	public void onMessageSent(MessageSentEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onChatCreated(ChatCreatedEvent event)
	{
		Logger.getLogger("").log(Level.INFO, "Chat Created");

		final CheeonkChat chat = event.getChat();
		final ConnectionKey key = event.getConnectionKey();

		addKeyPressHandler(new KeyPressHandler()
		{
			@Override
			public void onKeyPress(KeyPressEvent event)
			{
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				{
					dispatchAsync.execute(new SendMessage(key, chat, getMessageAreaText()), new SentMessage()
					{
						@Override
						public void got(boolean isSent)
						{
							if (isSent)
							{
								resetMessageArea();
							}
						}
					});

				}
			}

		});

		setTimer(new Timer()
		{
			@Override
			public void run()
			{
				dispatchAsync.execute(new GetMessages(key, chat), new GotMessages()
				{
					@Override
					public void got(CheeonkMessage[] messages)
					{
						for (CheeonkMessage message : messages)
						{
							addCheeonk(message.getFrom(), message.getBody());
						}

					}
				});

			}
		}, 1000);

	}

	@Deprecated
	@Override
	public void onChatReceived(ChatReceivedEvent event)
	{
		// TODO I might not need this in here
	}

	@Deprecated
	@Override
	public void onSignedin(SignedinEvent event)
	{
		// Not used
	}

	@Override
	public void onSignedout(SignedoutEvent event)
	{
		cancelTimer();
	}
}
