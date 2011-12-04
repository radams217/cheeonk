package com.cheeonk.client.widgets.chat;

import com.cheeonk.shared.message.IMessage;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CheeonkWidget extends Composite
{
	private final IMessage message;

	public CheeonkWidget(IMessage message)
	{
		this.message = message;

		HorizontalPanel panel = new HorizontalPanel();
		panel.setStyleName("cheeonk");

		VerticalPanel cheeonkPanel = new VerticalPanel();
		cheeonkPanel.add(new HTML(message.getFrom().toString() + ":"));
		cheeonkPanel.add(new HTML(this.message.getBody()));
		panel.add(cheeonkPanel);

		initWidget(panel);
		setStyleName("cheeonk");
	}

	public IMessage getMessage()
	{
		return message;
	}
}