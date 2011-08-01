package com.ryannadams.cheeonk.client.widgets.authentication;

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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.callback.Signedin;
import com.ryannadams.cheeonk.client.event.SignedinEvent;
import com.ryannadams.cheeonk.client.event.SignedoutEvent;
import com.ryannadams.cheeonk.client.handler.AuthenticationEventHandler;
import com.ryannadams.cheeonk.shared.action.Signin;
import com.ryannadams.cheeonk.shared.buddy.JabberId;

public class SigninWidget extends Composite implements AuthenticationEventHandler, ClickHandler
{
	private final DispatchAsync dispatchAsync;
	private final SimpleEventBus eventBus;

	private final TextBox usernameField;
	private final PasswordTextBox passwordField;
	private final HTML errorMessage;
	private final CheckBox staySignedIn;
	private final Button goButton;

	// TODO: Add Forgot Password? Link
	public SigninWidget(final SimpleEventBus eventBus)
	{
		this.dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

		this.eventBus = eventBus;
		this.eventBus.addHandler(SignedinEvent.TYPE, this);
		this.eventBus.addHandler(SignedoutEvent.TYPE, this);

		this.usernameField = new TextBox();
		this.passwordField = new PasswordTextBox();
		this.errorMessage = new HTML();
		this.errorMessage.setStyleName("signinPopupPanel-errorMessage");

		this.staySignedIn = new CheckBox("Stay signed in");

		this.goButton = new Button("go", this);

		this.passwordField.addKeyPressHandler(new KeyPressHandler()
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

		initWidget(panel);
		usernameField.setFocus(true);
	}

	protected void onLoginProblem(String message)
	{
		errorMessage.setHTML(message);
	}

	@Override
	public void onClick(ClickEvent event)
	{
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
					if (isConnected)
					{
						onLoginProblem("The username or password you entered is incorrect.");
					}
					else
					{
						onLoginProblem("Cannot connected to the cheeonk server.");
					}
				}
			}
		});
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