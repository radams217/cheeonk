package com.ryannadams.cheeonk.client.widgets;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FullLoginWidget extends LoginWidget
{
	private Button forgotPassword;

	public FullLoginWidget()
	{
		username = new TextBox();
		password = new PasswordTextBox();
		login = new Button("login");
		forgotPassword = new Button("Forgot Password");
	}

	@Override
	protected void initPanel()
	{
		VerticalPanel panel = new VerticalPanel();

		panel.add(new HTML("User Name:"));
		panel.add(username);
		panel.add(new HTML("Password:"));
		panel.add(password);
		panel.add(login);
		panel.add(forgotPassword);

		initWidget(panel);
	}

}
