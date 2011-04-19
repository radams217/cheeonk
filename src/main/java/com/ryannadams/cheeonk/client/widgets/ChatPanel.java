package com.ryannadams.cheeonk.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ChatPanel extends DialogBox
{
	private final HTML chatWindow;
	private final TextArea messageArea;
	private final ScrollPanel scrollPanel;
	private final Button close;

	public ChatPanel(String recipienttext)
	{
		super(false);

		chatWindow = new HTML();
		chatWindow.setStyleName("chat-Window");
		scrollPanel = new ScrollPanel();
		scrollPanel.add(chatWindow);
		messageArea = new TextArea();
		messageArea.setStyleName("chat-MessageArea");
		close = new Button("Close");

		addKeyPressHandler(new KeyPressHandler()
		{
			@Override
			public void onKeyPress(KeyPressEvent event)
			{
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				{
					addChatMessage("me", messageArea.getText());
					messageArea.setCursorPos(0);
				}

			}

		});

		close.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				hide();
			}
		});

		VerticalPanel panel = new VerticalPanel();
		panel.setStyleName("chat-Panel");
		panel.add(scrollPanel);
		panel.add(messageArea);

		add(panel);

		setText("Cheeonk with " + recipienttext);
	}

	public void addChatMessage(String from, String message)
	{
		String temp = chatWindow.getHTML();
		chatWindow.setHTML(temp + from + ":<BR/>" + message + "<BR/>");
		scrollPanel.scrollToBottom();
	}

	public void clearMessageText()
	{
		messageArea.setText("");
	}

	public String getMessageText()
	{
		return messageArea.getText();
	}

	public void addKeyPressHandler(KeyPressHandler keyPressHandler)
	{
		messageArea.addKeyPressHandler(keyPressHandler);
	}

}
