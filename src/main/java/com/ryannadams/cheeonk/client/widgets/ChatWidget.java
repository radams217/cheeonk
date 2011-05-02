package com.ryannadams.cheeonk.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Timer;
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
public class ChatWidget extends Composite
{
	private final VerticalPanel cheeonks;
	private final TextArea messageArea;
	private final ScrollPanel scrollPanel;

	private Timer timer;

	public ChatWidget()
	{
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
		// panel.addStyleName("chatWidget");
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

	public String getMessageAreaText()
	{
		return messageArea.getText();
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

}
