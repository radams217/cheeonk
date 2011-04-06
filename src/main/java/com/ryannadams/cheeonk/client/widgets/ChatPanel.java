package com.ryannadams.cheeonk.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.ClientChatImpl;
import com.ryannadams.cheeonk.client.IMessage;
import com.ryannadams.cheeonk.client.services.ChatService;
import com.ryannadams.cheeonk.client.services.ChatServiceAsync;

public class ChatPanel extends Composite
{
	private final ChatServiceAsync chatService = GWT.create(ChatService.class);

	private Label recipient;
	private Label chatWindow;
	private TextBox messageBox;
	private Button send;

	public ChatPanel()
	{
		recipient = new Label();
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

		// Create a new timer that calls Window.alert().
		Timer t = new Timer()
		{
			@Override
			public void run()
			{
				chatService.getMessages(
						new ClientChatImpl(recipient.getText()),
						new AsyncCallback<IMessage[]>()
						{

							@Override
							public void onSuccess(IMessage[] messages)
							{
								StringBuffer result = new StringBuffer();

								if (messages.length > 0)
								{
									result.append(messages[0].getBody());

									chatWindow.setText(result.toString());

								}

							}

							@Override
							public void onFailure(Throwable caught)
							{
								// TODO Auto-generated method stub

							}
						});
			}
		};

		// Schedule the timer to run once in 1 second.
		t.scheduleRepeating(1000);

	}

	public void setRecipient(String username)
	{
		this.recipient.setText(username);
	}

	public void addClickHandler(ClickHandler clickHandler)
	{
		send.addClickHandler(clickHandler);
	}

}
