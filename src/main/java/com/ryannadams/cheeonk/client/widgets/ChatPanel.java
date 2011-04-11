package com.ryannadams.cheeonk.client.widgets;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ChatPanel extends DialogBox
{
	private HTML chatWindow;
	private TextArea messageArea;

	public ChatPanel(String recipienttext)
	{
		super(true);

		chatWindow = new HTML();
		chatWindow.setStyleName("chat-Window");
		ScrollPanel scroll = new ScrollPanel();
		scroll.add(chatWindow);
		messageArea = new TextArea();
		messageArea.setStyleName("chat-MessageArea");

		addKeyPressHandler(new KeyPressHandler()
		{

			@Override
			public void onKeyPress(KeyPressEvent event)
			{
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				{
					addChatMessage("me", messageArea.getText());
					// messageArea.setText("");
				}

			}

		});

		VerticalPanel panel = new VerticalPanel();
		panel.setStyleName("chat-Panel");
		panel.add(scroll);
		panel.add(messageArea);

		add(panel);

		setText("Cheeonk with " + recipienttext);
	}

	public void addChatMessage(String from, String message)
	{
		String temp = chatWindow.getHTML();
		chatWindow.setHTML(temp + from + " [TIME]:<BR/>" + message + "<BR/>");
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
