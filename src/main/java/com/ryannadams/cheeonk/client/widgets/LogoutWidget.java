package com.ryannadams.cheeonk.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class LogoutWidget extends Composite
{
	private Label userName;
	private Button logout;

	public LogoutWidget()
	{
		userName = new Label();
		logout = new Button("Logout");

		HorizontalPanel panel = new HorizontalPanel();
		panel.add(new HTML("Logged in as "));
		panel.add(userName);
		panel.addStyleName("logoutWidget");
		panel.add(logout);

		initWidget(panel);

	}

	public void setUserName(String userName)
	{
		this.userName.setText(userName);
	}

	public String getUserName()
	{
		return userName.getText();
	}

	public void addClickHandler(ClickHandler clickHandler)
	{
		logout.addClickHandler(clickHandler);
	}
}
