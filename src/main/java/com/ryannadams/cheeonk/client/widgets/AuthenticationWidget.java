package com.ryannadams.cheeonk.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AuthenticationWidget extends Composite
{
	private final HorizontalPanel panel;
	private final Button signinButton;
	private final Button signoutButton;
	private final TextBox usernameField;
	private final PasswordTextBox passwordField;
	private final Button goButton;

	public AuthenticationWidget()
	{
		panel = new HorizontalPanel();
		signinButton = new Button("Sign in");
		signoutButton = new Button("Sign out");
		usernameField = new TextBox();
		passwordField = new PasswordTextBox();
		goButton = new Button("go");

		signinButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				final PopupPanel popup = new PopupPanel(true);

				popup.setPopupPositionAndShow(new PopupPanel.PositionCallback()
				{
					@Override
					public void setPosition(int offsetWidth, int offsetHeight)
					{
						// TODO: Take out the hardcoded 200
						int left = (getAbsoluteLeft() + getOffsetWidth()) - 200;
						int top = signinButton.getAbsoluteTop() + signinButton.getOffsetHeight();
						popup.setPopupPosition(left, top);
					}
				});

				popup.add(new SigninPanel()
				{
					@Override
					public void onClick(ClickEvent event)
					{
						popup.hide();
					}

				});

				popup.show();
			}
		});

		panel.add(signinButton);
		initWidget(panel);
	}

	public boolean validate()
	{
		if (usernameField.getText().equals("") || passwordField.getText().equals(""))
		{
			return false;
		}

		return true;
	}

	public void signedIn()
	{
		panel.clear();
		panel.add(new HTML("Logged in as " + usernameField.getText()));
		panel.add(signoutButton);
	}

	public void signedOff()
	{
		panel.clear();
		panel.add(signinButton);
	}

	public String getUsername()
	{
		return usernameField.getText();
	}

	public String getPassword()
	{
		return passwordField.getText();
	}

	public void addGoClickHandler(ClickHandler clickHandler)
	{
		goButton.addClickHandler(clickHandler);
	}

	public void addSignoutClickHandler(ClickHandler clickHandler)
	{
		signoutButton.addClickHandler(clickHandler);
	}

	private abstract class SigninPanel extends Composite implements ClickHandler
	{
		public SigninPanel()
		{
			goButton.addClickHandler(this);

			VerticalPanel panel = new VerticalPanel();
			panel.setStyleName("signinPanel");
			panel.add(new HTML("Username:"));
			panel.add(usernameField);
			panel.add(new HTML("Password:"));
			panel.add(passwordField);
			panel.add(goButton);

			initWidget(panel);
		}

		@Override
		public abstract void onClick(ClickEvent event);
	}
}
