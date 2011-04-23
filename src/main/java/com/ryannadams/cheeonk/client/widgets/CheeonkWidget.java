package com.ryannadams.cheeonk.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CheeonkWidget extends Composite
{
	private final VerticalPanel cheeonks;
	private final TextArea messageArea;
	private final ScrollPanel scrollPanel;
	private final Button close;

	public CheeonkWidget()
	{
		cheeonks = new VerticalPanel();
		scrollPanel = new ScrollPanel();
		scrollPanel.add(cheeonks);

		messageArea = new TextArea();
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

		close = new Button("Close", new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				// hide();
			}
		});

		VerticalPanel panel = new VerticalPanel();
		panel.add(cheeonks);
		panel.add(messageArea);
		// panel.add(close);

		initWidget(panel);
	}

	public String getMessage()
	{
		return messageArea.getText();
	}

	public void addClickHandler(ClickHandler clickHandler)
	{
		close.addClickHandler(clickHandler);
	}

	public void addKeyPressHandler(KeyPressHandler keyPressHandler)
	{
		messageArea.addKeyPressHandler(keyPressHandler);
	}

	public void addCheeonk(String sender, String message)
	{
		cheeonks.add(new Cheeonk(sender, message));
		scrollPanel.scrollToBottom();
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
