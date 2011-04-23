package com.ryannadams.cheeonk.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author radams217
 * 
 *         The AuthenticationWidget combines the login and logout tasks into one
 *         widget. The elements on the panel can be cleared and reset based on
 *         whether the user is signed in or sign out of the server. By Default
 *         the panel is built and set to the sign in state. A sign in button
 *         will appear on the panel and all credentials can be entered into a
 *         pop up window. The logout state contains text indicating the user
 *         name logged in and a logout button. All input in this class can be
 *         validating by calling the validate method.
 */
public class AuthenticationWidget extends Composite
{
	private final HorizontalPanel panel;
	private final Button signinButton;
	private final Button signoutButton;
	private final TextBox usernameField;
	private final PasswordTextBox passwordField;
	private final Button goButton;

	// TODO: Add Remember Me CheckBox
	// TODO: Add Forgot Password? Link

	/**
	 * Default Constructor the initiates the panel to the sign in state.
	 */
	public AuthenticationWidget()
	{
		signinButton = new Button("Sign in", new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				final SigninPopupPanel signinPopupPanel = new SigninPopupPanel();

				signinPopupPanel.setPopupPositionAndShow(new PopupPanel.PositionCallback()
				{
					@Override
					public void setPosition(int offsetWidth, int offsetHeight)
					{
						int left = (getAbsoluteLeft() + getOffsetWidth()) - signinPopupPanel.getOffsetWidth();
						int top = signinButton.getAbsoluteTop() + signinButton.getOffsetHeight();
						signinPopupPanel.setPopupPosition(left, top);
					}
				});

				signinPopupPanel.show();
				usernameField.setFocus(true);
			}
		});

		signoutButton = new Button("Sign out", new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				usernameField.setText("");
				passwordField.setText("");
			}
		});

		usernameField = new TextBox();
		passwordField = new PasswordTextBox();
		goButton = new Button("go");

		passwordField.addKeyPressHandler(new KeyPressHandler()
		{
			@Override
			public void onKeyPress(KeyPressEvent event)
			{
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				{
					goButton.click();
				}

			}

		});

		panel = new HorizontalPanel();
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

	private class SigninPopupPanel extends PopupPanel
	{
		public SigninPopupPanel()
		{
			super(true);

			VerticalPanel panel = new VerticalPanel();
			panel.setStyleName("signinPanel");
			panel.add(new HTML("Username:"));
			panel.add(usernameField);
			panel.add(new HTML("Password:"));
			panel.add(passwordField);
			panel.add(goButton);

			goButton.addClickHandler(new ClickHandler()
			{
				@Override
				public void onClick(ClickEvent event)
				{
					hide();
				}
			});

			add(panel);
		}

	}
}
