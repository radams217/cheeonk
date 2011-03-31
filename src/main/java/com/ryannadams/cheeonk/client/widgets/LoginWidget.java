package com.ryannadams.cheeonk.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class LoginWidget extends Composite
{
	protected TextBox username;
	protected PasswordTextBox password;
	protected Button login;

	public LoginWidget()
	{
		username = new TextBox();
		password = new PasswordTextBox();
		login = new Button("login");

		initPanel();
	}

	protected void initPanel()
	{
		HorizontalPanel panel = new HorizontalPanel();

		panel.add(new HTML("User Name:"));
		panel.add(username);
		panel.add(new HTML("Password:"));
		panel.add(password);
		panel.add(login);

		initWidget(panel);
	}

	public String getUsername()
	{
		return username.getText();
	}

	public String getPassword()
	{
		return password.getText();
	}

	public void addClickHandler(ClickHandler clickHandler)
	{
		login.addClickHandler(clickHandler);
	}
}
