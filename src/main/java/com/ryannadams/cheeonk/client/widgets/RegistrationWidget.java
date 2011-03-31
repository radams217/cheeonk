package com.ryannadams.cheeonk.client.widgets;

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
	private TextBox emailField;
	private TextBox firstNameField;
	private TextBox lastNameField;
	private DatePickerTextBox birthDateField;
	private PasswordTextBox passwordField;
	private PasswordTextBox passwordConfirmField;
	private Button registerButton;
	private Button clearButton;

	public RegistrationWidget()
	{
		VerticalPanel panel = new VerticalPanel();

		emailField = new TextBox();
		firstNameField = new TextBox();
		lastNameField = new TextBox();

		passwordField = new PasswordTextBox();
		passwordConfirmField = new PasswordTextBox();

		birthDateField = new DatePickerTextBox();

		registerButton = new Button("Register");
		registerButton.addClickHandler(this);

		clearButton = new Button("Clear");

		clearButton.addClickHandler(new ClickHandler()
		{

			@Override
			public void onClick(ClickEvent event)
			{
				emailField.setText("");
				firstNameField.setText("");
				lastNameField.setText("");
				passwordField.setText("");
				passwordConfirmField.setText("");
			}
		});

		panel.addStyleName("registrationWidget");
		panel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);

		panel.add(new HTML("Email:"));
		panel.add(emailField);

		panel.add(new HTML("Password:"));
		panel.add(passwordField);

		panel.add(new HTML("Confirm Password:"));
		panel.add(passwordConfirmField);

		HorizontalPanel namePanel = new HorizontalPanel();
		namePanel.add(new HTML("First Name:"));
		namePanel.add(firstNameField);
		namePanel.add(new HTML("Last Name:"));
		namePanel.add(lastNameField);
		panel.add(namePanel);

		panel.add(new HTML("Birthdate:"));
		panel.add(birthDateField);

		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(registerButton);
		buttonPanel.add(clearButton);
		panel.add(buttonPanel);

		initWidget(panel);

	}

	private boolean validate()
	{
		return true;

	}

	@Override
	public void onClick(ClickEvent event)
	{
		validate();

		// return;

	}

}
