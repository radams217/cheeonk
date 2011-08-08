package com.cheeonk.client.widgets.chat;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.cheeonk.client.callback.SentMessage;
import com.cheeonk.client.event.MessageSentEvent;
import com.cheeonk.client.handler.MessageEventHandler;
import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.action.SendMessage;
import com.cheeonk.shared.buddy.IBuddy;
import com.cheeonk.shared.buddy.JabberId;
import com.cheeonk.shared.event.MessageReceivedEvent;
import com.cheeonk.shared.message.CheeonkMessage;
import com.cheeonk.shared.message.IMessage;
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
		cheeonks.addStyleName("chatWidget-Cheeonks");

		scrollPanel = new ScrollPanel();
		scrollPanel.addStyleName("chatWidget-ScrollCheeonks");
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
		return new CheeonkMessage(participant.getJabberId(), new JabberId("me"), messageArea.getText());
	}

	private class Cheeonk extends Composite implements ClickHandler
	{
		private final PushButton cheeonkCastButton;
		private final IMessage message;

		public Cheeonk(IMessage message)
		{
			this.message = message;

			VerticalPanel cheeonkPanel = new VerticalPanel();
			cheeonkPanel.add(new HTML(message.getFrom().toString() + ":"));
			cheeonkPanel.add(new HTML(this.message.getBody()));

			cheeonkCastButton = new PushButton("C", this);
			cheeonkCastButton.setStyleName("cheeonk-Cast");

			HorizontalPanel panel = new HorizontalPanel();
			panel.setStyleName("cheeonk");
			panel.add(cheeonkPanel);
			panel.add(cheeonkCastButton);

			initWidget(panel);
			setStyleName("cheeonk");
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
		// // Check where to route the message
		// if (!event.getMessage().getFrom().equals(participant.getJabberId()))
		// {
		// return;
		// }
		//
		// addCheeonk(event.getMessage());
	}

	@Override
	public void onMessageSent(MessageSentEvent event)
	{

	}
}
