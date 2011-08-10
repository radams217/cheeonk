package com.cheeonk.client.widgets.registration;

import com.cheeonk.client.ImageResources;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RegistrationWidget extends Composite implements ClickHandler
{
	private final VerticalPanel panel;
	private final TextBox usernameField;
	private final TextBox nameField;
	private final TextBox emailField;
	private final PasswordTextBox passwordField;
	private final PasswordTextBox passwordConfirmField;
	private final Button registerButton;
	private final Button clearButton;

	public RegistrationWidget()
	{
		panel = new VerticalPanel();
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		usernameField = new TextBox();
		usernameField.setStyleName("RegistrationWidget-Texbox");
		nameField = new TextBox();
		nameField.setStyleName("RegistrationWidget-Texbox");
		emailField = new TextBox();
		emailField.setStyleName("RegistrationWidget-Texbox");
		passwordField = new PasswordTextBox();
		passwordField.setStyleName("RegistrationWidget-Texbox");
		passwordConfirmField = new PasswordTextBox();
		passwordConfirmField.setStyleName("RegistrationWidget-Texbox");

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

		FlowPanel cheeonkPanel = new FlowPanel();
		cheeonkPanel.add(new HTML("Registering for a "));
		cheeonkPanel.add(new Image(ImageResources.INSTANCE.getSmallLogo()));
		cheeonkPanel.add(new HTML(" account is as easy as filling out the fields below."));
		panel.add(cheeonkPanel);

		HorizontalPanel usernamePanel = new HorizontalPanel();
		HTML usernameLabel = new HTML("Username:");
		usernameLabel.setStyleName("RegistrationWidget-Label");
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameField);
		panel.add(usernamePanel);

		HorizontalPanel fullNamePanel = new HorizontalPanel();
		HTML fullnameLabel = new HTML("Full Name:");
		fullnameLabel.setStyleName("RegistrationWidget-Label");
		fullNamePanel.add(fullnameLabel);
		fullNamePanel.add(nameField);
		panel.add(fullNamePanel);

		HorizontalPanel emailPanel = new HorizontalPanel();
		HTML emailLabel = new HTML("Email:");
		emailLabel.setStyleName("RegistrationWidget-Label");
		emailPanel.add(emailLabel);
		emailPanel.add(emailField);
		panel.add(emailPanel);

		HorizontalPanel passwordPanel = new HorizontalPanel();
		HTML passwordLabel = new HTML("Password:");
		passwordLabel.setStyleName("RegistrationWidget-Label");
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		panel.add(passwordPanel);

		HorizontalPanel confirmPasswordPanel = new HorizontalPanel();
		HTML confirmPasswordLabel = new HTML("Confirm Password:");
		confirmPasswordLabel.setStyleName("RegistrationWidget-Label");
		confirmPasswordPanel.add(confirmPasswordLabel);
		confirmPasswordPanel.add(passwordConfirmField);
		panel.add(confirmPasswordPanel);

		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(registerButton);
		buttonPanel.add(clearButton);
		panel.add(buttonPanel);

		initWidget(panel);
		setStyleName("RegistrationWidget");
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

	// Possibly create an event listener for on successful registration
	public void onSuccess()
	{
		panel.clear();
		panel.add(new HTML("Registration Successful!  Summary:"));
		panel.add(new HTML("Username: " + usernameField.getText()));
		panel.add(new HTML("Full Name: " + nameField.getText()));
		panel.add(new HTML("Email: " + emailField.getText()));
		panel.add(new HTML("Password: Hidden"));

	}
}
