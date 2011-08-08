package com.cheeonk.client.widgets.authentication;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.cheeonk.client.ImageResources;
import com.cheeonk.client.callback.Signedin;
import com.cheeonk.client.event.SignedinEvent;
import com.cheeonk.client.event.SignedoutEvent;
import com.cheeonk.client.handler.AuthenticationEventHandler;
import com.cheeonk.shared.action.Signin;
import com.cheeonk.shared.buddy.JabberId;
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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

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
		this.usernameField.addStyleName("signinWidget-usernameField");
		this.passwordField = new PasswordTextBox();
		this.passwordField.addStyleName("signinWidget-passwordField");
		this.errorMessage = new HTML();
		this.errorMessage.setStyleName("signinWidget-errorMessage");

		this.staySignedIn = new CheckBox("Stay signed in");
		this.staySignedIn.setStyleName("signinWidget-staySignedin");

		this.goButton = new Button("Sign in", this);

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
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.add(new HTML("Sign into your"));
		HorizontalPanel cheeonkPanel = new HorizontalPanel();
		cheeonkPanel.add(new Image(ImageResources.INSTANCE.getSmallLogo()));
		cheeonkPanel.add(new HTML("Account."));
		panel.add(cheeonkPanel);
		HorizontalPanel usernamePanel = new HorizontalPanel();
		usernamePanel.add(new HTML("Username:"));
		usernamePanel.add(usernameField);
		HorizontalPanel passwordPanel = new HorizontalPanel();
		passwordPanel.add(new HTML("Password:"));
		passwordPanel.add(passwordField);
		panel.add(usernamePanel);
		panel.add(passwordPanel);
		panel.add(errorMessage);
		panel.add(staySignedIn);
		panel.add(goButton);

		initWidget(panel);
		setStyleName("signinWidget");
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