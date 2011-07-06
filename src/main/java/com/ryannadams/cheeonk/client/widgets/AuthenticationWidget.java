package com.ryannadams.cheeonk.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.event.SignedinEvent;
import com.ryannadams.cheeonk.client.event.SignedoutEvent;
import com.ryannadams.cheeonk.client.handler.AuthenticationEventHandler;

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
public class AuthenticationWidget extends Composite implements AuthenticationEventHandler
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
	 * 
	 * @param eventBus
	 */
	public AuthenticationWidget(SimpleEventBus eventBus)
	{
		eventBus.addHandler(SignedinEvent.TYPE, this);
		eventBus.addHandler(SignedoutEvent.TYPE, this);

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

	private class SigninPopupPanel extends DecoratedPopupPanel
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

	@Override
	public void onSignedin(SignedinEvent event)
	{
		HTML loggedinAs = new HTML("Logged in as " + getUsername());
		loggedinAs.addStyleName("authenticationWidget-LoggedIn");

		panel.clear();
		panel.add(loggedinAs);
		panel.add(signoutButton);
	}

	@Override
	public void onSignedout(SignedoutEvent event)
	{
		panel.clear();
		panel.add(signinButton);
	}

}
