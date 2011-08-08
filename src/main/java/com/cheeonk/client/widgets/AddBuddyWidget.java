package com.cheeonk.client.widgets;

import com.cheeonk.shared.buddy.CheeonkBuddy;
import com.cheeonk.shared.buddy.IBuddy;
import com.cheeonk.shared.buddy.JabberId;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AddBuddyWidget extends Composite implements ClickHandler
{
	private final VerticalPanel panel;
	private final TextBox jabberId;
	private final TextBox buddyName;
	private final Button okButton;

	public AddBuddyWidget()
	{
		this.panel = new VerticalPanel();

		this.jabberId = new TextBox();
		this.jabberId.setStyleName("AddBuddyWidget-jabberId");
		this.buddyName = new TextBox();
		this.buddyName.setStyleName("AddBuddyWidget-buddyName");

		this.buddyName.addKeyPressHandler(new KeyPressHandler()
		{
			@Override
			public void onKeyPress(KeyPressEvent event)
			{
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				{
					okButton.click();
				}
			}
		});

		HorizontalPanel jabberPanel = new HorizontalPanel();
		jabberPanel.add(new HTML("Jabber Id:"));
		jabberPanel.add(jabberId);

		HorizontalPanel buddyNamePanel = new HorizontalPanel();
		buddyNamePanel.add(new HTML("Display Name:"));
		buddyNamePanel.add(buddyName);

		this.okButton = new Button("Add Buddy");

		panel.add(jabberPanel);
		panel.add(buddyNamePanel);
		panel.add(okButton);

		okButton.addClickHandler(this);

		initWidget(panel);
		setStyleName("AddBuddyWidget");
	}

	public void clear()
	{
		jabberId.setText("");
		buddyName.setText("");
	}

	public IBuddy getBuddy()
	{
		return new CheeonkBuddy(new JabberId(jabberId.getText()), buddyName.getText());
	}

	@Override
	public void onClick(ClickEvent event)
	{
		// TODO Auto-generated method stub

	}

}
