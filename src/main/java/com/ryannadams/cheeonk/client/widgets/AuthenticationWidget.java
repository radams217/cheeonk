package com.ryannadams.cheeonk.client.widgets;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.callback.Signedin;
import com.ryannadams.cheeonk.client.callback.Signedout;
import com.ryannadams.cheeonk.client.event.SignedinEvent;
import com.ryannadams.cheeonk.client.event.SignedoutEvent;
import com.ryannadams.cheeonk.client.handler.AuthenticationEventHandler;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.Signin;
import com.ryannadams.cheeonk.shared.action.Signout;
import com.ryannadams.cheeonk.shared.buddy.JabberId;

/**
 * @author radams217
 * 
 *         The AuthenticationWidget combines the login and logout tasks into one
 *         widget. The elements on the panel can be cleared and reset based on
 *         whether the user is signed in or sign out of the server. By Default
 *         the panel is built and set to the sign in state. A sign in button
 *         will appear on the panel and all credentials can be entered into a
 *         pop up window. The logout state contains text indicating the user
 *         name logged in and a logout button.
 */
public class AuthenticationWidget extends Composite implements AuthenticationEventHandler
{
	private final DispatchAsync dispatchAsync;
	private final HorizontalPanel panel;
	private final Button signinButton;
	private final Button signoutButton;
	private final SigninPopupPanel signinPopupPanel;

	/**
	 * Default Constructor the initiates the panel to the sign in state.
	 * 
	 * @param eventBus
	 */
	public AuthenticationWidget(final SimpleEventBus eventBus)
	{
		dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

		eventBus.addHandler(SignedinEvent.TYPE, this);
		eventBus.addHandler(SignedoutEvent.TYPE, this);

		this.signinPopupPanel = new SigninPopupPanel(eventBus);

		this.signinButton = new Button("Sign in", new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
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
			}
		});

		this.signoutButton = new Button("Sign out", new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				dispatchAsync.execute(new Signout(ConnectionKey.get()), new Signedout()
				{
					@Override
					public void got(boolean isSignedout)
					{
						if (isSignedout)
						{
							eventBus.fireEvent(new SignedoutEvent());
						}

					}
				});

			}
		});

		this.panel = new HorizontalPanel();
		this.panel.add(signinButton);

		initWidget(panel);
	}

	private class SigninPopupPanel extends DecoratedPopupPanel implements AuthenticationEventHandler
	{
		private final DispatchAsync dispatchAsync;

		private final TextBox usernameField;
		private final PasswordTextBox passwordField;
		private final HTML errorMessage;
		private final CheckBox staySignedIn;
		private final Button goButton;

		// TODO: Add Forgot Password? Link

		public SigninPopupPanel(final SimpleEventBus eventBus)
		{
			super(true);

			dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

			eventBus.addHandler(SignedinEvent.TYPE, this);
			eventBus.addHandler(SignedoutEvent.TYPE, this);

			usernameField = new TextBox();
			passwordField = new PasswordTextBox();
			errorMessage = new HTML();
			errorMessage.setStyleName("signinPopupPanel-errorMessage");

			staySignedIn = new CheckBox("Stay signed in");

			goButton = new Button("go", new ClickHandler()
			{
				@Override
				public void onClick(ClickEvent event)
				{
					hide();

					dispatchAsync.execute(new Signin(usernameField.getText(), passwordField.getText()), new Signedin()
					{
						@Override
						public void got(String connectionId, JabberId jabberId, boolean isConnected, boolean isSignedin)
						{
							if (isSignedin)
							{
								eventBus.fireEvent(new SignedinEvent(connectionId, jabberId));
							}
							else
							{
								// if (isConnected)
								// {
								errorMessage.setHTML("The username or password you entered is incorrect.");
								// }
								// else
								// {
								// errorMessage.setHTML("Cannot connected to the cheeonk server.");
								// }
							}
						}
					});
				}
			});

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

			VerticalPanel panel = new VerticalPanel();
			panel.setStyleName("signinPanel");
			panel.add(new HTML("Username:"));
			panel.add(usernameField);
			panel.add(new HTML("Password:"));
			panel.add(passwordField);
			panel.add(errorMessage);
			panel.add(staySignedIn);
			panel.add(goButton);

			add(panel);
		}

		public String getUsername()
		{
			return usernameField.getText();
		}

		@Override
		public void onSignedin(SignedinEvent event)
		{
			passwordField.setText("");
		}

		@Override
		public void onSignedout(SignedoutEvent event)
		{
			usernameField.setText("");
		}
	}

	@Override
	public void onSignedin(SignedinEvent event)
	{
		HTML loggedinAs = new HTML("Logged in as " + signinPopupPanel.getUsername());
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
