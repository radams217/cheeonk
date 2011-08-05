package com.cheeonk.client.widgets.authentication;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.cheeonk.client.callback.Signedout;
import com.cheeonk.client.event.SignedinEvent;
import com.cheeonk.client.event.SignedoutEvent;
import com.cheeonk.client.handler.AuthenticationEventHandler;
import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.action.Signout;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;

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
	private final SigninWidget signinWidget;

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

		final PopupPanel signinWidgetPopup = new PopupPanel(true);
		signinWidgetPopup.setStyleName("signinWidgetPopup");

		this.signinWidget = new SigninWidget(eventBus)
		{
			@Override
			public void onClick(ClickEvent event)
			{
				super.onClick(event);
				signinWidgetPopup.hide();
			}

			@Override
			protected void onLoginProblem(String message)
			{
				super.onLoginProblem(message);
				signinWidgetPopup.show();
			}
		};

		signinWidgetPopup.add(signinWidget);

		this.signinButton = new Button("Sign in", new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				signinWidgetPopup.setPopupPositionAndShow(new PopupPanel.PositionCallback()
				{
					@Override
					public void setPosition(int offsetWidth, int offsetHeight)
					{
						int left = (getAbsoluteLeft() + getOffsetWidth()) - signinWidgetPopup.getOffsetWidth();
						int top = signinButton.getAbsoluteTop() + signinButton.getOffsetHeight();
						signinWidgetPopup.setPopupPosition(left, top);
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
		setStyleName("authenticationWidget");
	}

	@Override
	public void onSignedin(SignedinEvent event)
	{
		HTML loggedinAs = new HTML("Logged in as " + event.getJabberId().toString());
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
