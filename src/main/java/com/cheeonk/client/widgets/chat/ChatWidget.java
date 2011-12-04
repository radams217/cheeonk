package com.cheeonk.client.widgets.chat;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.cheeonk.client.ImageResources;
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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
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
public abstract class ChatWidget extends AbstractChatWidget implements MessageEventHandler
{
	private final ChatHeaderWidget chatHeaderWidget;
	private final VerticalPanel cheeonks;
	private final TextArea messageArea;
	private final ScrollPanel scrollPanel;

	private final DispatchAsync dispatchAsync;

	public ChatWidget(final SimpleEventBus eventBus)
	{
		eventBus.addHandler(MessageReceivedEvent.TYPE, this);

		this.dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

		this.chatHeaderWidget = new ChatHeaderWidget();

		this.cheeonks = new VerticalPanel();
		this.cheeonks.addStyleName("chatWidget-Cheeonks");

		this.scrollPanel = new ScrollPanel();
		this.scrollPanel.addStyleName("chatWidget-ScrollCheeonks");
		this.scrollPanel.add(cheeonks);

		this.messageArea = new TextArea();
		this.messageArea.addStyleName("chatWidget-MessageArea");

		VerticalPanel panel = new VerticalPanel();
		panel.addStyleName("chatWidget");
		panel.add(chatHeaderWidget);
		panel.add(scrollPanel);
		panel.add(messageArea);

		this.messageArea.addKeyPressHandler(new KeyPressHandler()
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
							reset();
							addMessage(message);

							eventBus.fireEvent(new MessageSentEvent(message));
						}
					});

				}
			}

		});

		initWidget(panel);
	}

	protected IMessage getMessage()
	{
		return new CheeonkMessage(getParticipant().getJabberId(), new JabberId("me"), messageArea.getText());
	}

	public void reset()
	{
		messageArea.setCursorPos(0);
		messageArea.setText("");
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

	@Override
	public void addMessage(IMessage message)
	{
		cheeonks.add(new CheeonkWidget(message));
		scrollPanel.scrollToBottom();
	}

	@Override
	public abstract IBuddy getParticipant();

	private class ChatHeaderWidget extends Composite
	{
		private final HorizontalPanel panel;

		private final PushButton minimizeButton;
		private final PushButton maximizeButton;
		private final PushButton closeButton;

		public ChatHeaderWidget()
		{
			panel = new HorizontalPanel();

			HTML buddyName = new HTML(getParticipant().getName());
			buddyName.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			panel.add(buddyName);

			HorizontalPanel buttonPanel = new HorizontalPanel();
			buttonPanel.setStyleName("chatHeaderWidget-buttonPanel");
			minimizeButton = new PushButton(new Image(ImageResources.INSTANCE.getMinimizeSquare()), new ClickHandler()
			{
				@Override
				public void onClick(ClickEvent event)
				{
					minimize();
				}
			});
			minimizeButton.setStylePrimaryName("chatHeaderWidget-headerButton");
			maximizeButton = new PushButton(new Image(ImageResources.INSTANCE.getMaximizeSquare()), new ClickHandler()
			{
				@Override
				public void onClick(ClickEvent event)
				{
					maximize();
				}
			});
			maximizeButton.setStylePrimaryName("chatHeaderWidget-headerButton");
			closeButton = new PushButton(new Image(ImageResources.INSTANCE.getCloseSquare()), new ClickHandler()
			{
				@Override
				public void onClick(ClickEvent event)
				{
					close();
				}
			});
			closeButton.setStylePrimaryName("chatHeaderWidget-headerButton");

			buttonPanel.add(minimizeButton);
			buttonPanel.add(maximizeButton);
			buttonPanel.add(closeButton);
			panel.add(buttonPanel);

			initWidget(panel);
			setStyleName("chatHeaderWidget");
		}
	}
}
