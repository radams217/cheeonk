package com.ryannadams.cheeonk.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ChatPanel extends DialogBox
{

	private Label username;
	private Label history;
	private TextBox message;
	private Button send;

	public ChatPanel(boolean autoHide)
	{
		super(autoHide);
		username = new Label();
		history = new Label();
		message = new TextBox();
		send = new Button("Send");

		VerticalPanel panel = new VerticalPanel();
		panel.setStyleName("Chat-Popup");
		panel.add(username);
		panel.add(history);
		panel.add(message);
		panel.add(send);

		setWidget(panel);

		setTitle(username.getText());

		// Create a new timer that calls Window.alert().
		Timer t = new Timer()
		{
			@Override
			public void run()
			{
				messageService.getMessages(new AsyncCallback<String[]>()
				{

					@Override
					public void onSuccess(String[] result)
					{
						history.setText(result[result.length - 1]);
					}

					@Override
					public void onFailure(Throwable caught)
					{
						// TODO Auto-generated method stub

					}
				});
			}
		};

		// Schedule the timer to run once in 5 seconds.
		t.scheduleRepeating(2000);

	}

	public void setHistory(String history)
	{
		this.history.setText(history);
	}

	public void setUsername(String username)
	{
		this.username.setText(username);
	}

	public void addClickHandler(ClickHandler clickHandler)
	{
		send.addClickHandler(clickHandler);
	}

}
