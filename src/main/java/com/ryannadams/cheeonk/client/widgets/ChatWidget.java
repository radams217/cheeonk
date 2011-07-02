package com.ryannadams.cheeonk.client.widgets;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.callback.SentMessage;
import com.ryannadams.cheeonk.client.event.MessageSentEvent;
import com.ryannadams.cheeonk.client.handler.MessageEventHandler;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.SendMessage;
import com.ryannadams.cheeonk.shared.buddy.IBuddy;
import com.ryannadams.cheeonk.shared.buddy.JabberId;
import com.ryannadams.cheeonk.shared.event.MessageReceivedEvent;
import com.ryannadams.cheeonk.shared.message.CheeonkMessage;
import com.ryannadams.cheeonk.shared.message.IMessage;

/**
 * @author radams217
 * 
 *         The ChatWidget is essentially a chat window that a user can send and
 *         receive messages from other users. The timer is used to poll messages
 *         coming from the server. This widget can be added to a dialog box or
 *         any type of panel.
 */
public class ChatWidget extends Composite implements MessageEventHandler
{
	private final VerticalPanel cheeonks;
	private final TextArea messageArea;
	private final ScrollPanel scrollPanel;

	private final DispatchAsync dispatchAsync;

	private final IBuddy participant;

	public ChatWidget(final SimpleEventBus eventBus, final IBuddy participant)
	{
		eventBus.addHandler(MessageReceivedEvent.TYPE, this);

		this.participant = participant;

		dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

		cheeonks = new VerticalPanel();
		// scroll panel is set need to set the inner vertical panel
		// cheeonks.addStyleName("cheeonkWidget-Cheeonks");
		scrollPanel = new ScrollPanel();
		scrollPanel.addStyleName("chatWidget-Cheeonks");
		scrollPanel.add(cheeonks);

		messageArea = new TextArea();
		messageArea.addStyleName("chatWidget-MessageArea");

		VerticalPanel panel = new VerticalPanel();
		panel.addStyleName("chatWidget");
		panel.add(scrollPanel);
		panel.add(messageArea);

		addKeyPressHandler(new KeyPressHandler()
		{
			@Override
			public void onKeyPress(KeyPressEvent event)
			{
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				{
					dispatchAsync.execute(new SendMessage(ConnectionKey.get(), getMessage()), new SentMessage()
					{
						@Override
						public void got(IMessage message)
						{
							resetMessageArea();
							addCheeonk(message);

							eventBus.fireEvent(new MessageSentEvent(message));
						}
					});

				}
			}

		});

		initWidget(panel);
	}

	public void addKeyPressHandler(KeyPressHandler handler)
	{
		messageArea.addKeyPressHandler(handler);
	}

	public void addCheeonk(IMessage message)
	{
		cheeonks.add(new Cheeonk(message));
		scrollPanel.scrollToBottom();
	}

	public void resetMessageArea()
	{
		messageArea.setCursorPos(0);
		messageArea.setText("");
	}

	public IMessage getMessage()
	{
		return new CheeonkMessage(participant.getJabberId(), new JabberId(ConnectionKey.get().getUsername()), messageArea.getText());
	}

	private class Cheeonk extends Composite implements ClickHandler
	{
		private final HTML cheeonk;
		private final PushButton cheeonkCastButton;

		private final IMessage message;

		public Cheeonk(IMessage message)
		{
			this.message = message;

			cheeonk = new HTML(message.getFrom().toString() + ": " + message.getBody());
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
		// Check where to route the message
		if (!event.getMessage().getFrom().toString().equals(participant.getJabberId().toString() + "/Smack"))
		{
			return;
		}

		addCheeonk(event.getMessage());
	}

	@Override
	public void onMessageSent(MessageSentEvent event)
	{

	}
}
