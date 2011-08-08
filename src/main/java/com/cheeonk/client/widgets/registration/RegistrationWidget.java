package com.cheeonk.client.widgets.registration;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RegistrationWidget extends Composite implements ClickHandler
{
	private TextBox usernameField;
	private TextBox nameField;
	private TextBox emailField;
	private PasswordTextBox passwordField;
	private PasswordTextBox passwordConfirmField;
	private Button registerButton;
	private Button clearButton;

	public RegistrationWidget()
	{
		VerticalPanel panel = new VerticalPanel();

		usernameField = new TextBox();
		nameField = new TextBox();
		emailField = new TextBox();
		passwordField = new PasswordTextBox();
		passwordConfirmField = new PasswordTextBox();

		registerButton = new Button("Register");
		registerButton.addClickHandler(this);

		clearButton = new Button("Clear");

		clearButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				usernameField.setText("");
				nameField.setText("");
				emailField.setText("");
				passwordField.setText("");
				passwordConfirmField.setText("");
			}
		});

		panel.addStyleName("registrationWidget");

		panel.add(new HTML("Username:"));
		panel.add(usernameField);
		panel.add(new HTML("Full Name:"));
		panel.add(nameField);
		panel.add(new HTML("Email:"));
		panel.add(emailField);
		panel.add(new HTML("Password:"));
		panel.add(passwordField);
		panel.add(new HTML("Confirm Password:"));
		panel.add(passwordConfirmField);

		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(registerButton);
		buttonPanel.add(clearButton);
		panel.add(buttonPanel);

		initWidget(panel);

	}

	public String getUsername()
	{
		return usernameField.getText();
	}

	public String getPassword()
	{
		return passwordField.getText();
	}

	public String getName()
	{
		return nameField.getText();
	}

	public String getEmail()
	{
		return emailField.getText();
	}

	private boolean validate()
	{
		return true;

	}

	@Override
	public void onClick(ClickEvent event)
	{
		if (!validate())
		{
			return;
		}

	}

}
