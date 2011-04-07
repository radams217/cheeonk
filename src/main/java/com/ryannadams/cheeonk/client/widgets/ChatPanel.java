package com.ryannadams.cheeonk.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ChatPanel extends Composite
{
	private Label recipient;
	private Label chatWindow;
	private TextBox messageBox;
	private Button send;

	public ChatPanel(String recipienttext)
	{
		recipient = new Label(recipienttext);
		chatWindow = new Label();
		chatWindow.setStyleName("Chat-Window");
		messageBox = new TextBox();
		send = new Button("Send");

		VerticalPanel panel = new VerticalPanel();
		panel.setStyleName("Chat-Popup");
		panel.add(recipient);
		panel.add(chatWindow);
		panel.add(messageBox);
		panel.add(send);

		initWidget(panel);
	}

	public void setChatWindow(String text)
	{
		this.chatWindow.setText(text);

	}

	public void setRecipient(String username)
	{
		this.recipient.setText(username);
	}

	public String getMessageText()
	{
		return messageBox.getText();
	}

	public void addClickHandler(ClickHandler clickHandler)
	{
		send.addClickHandler(clickHandler);
	}

}
