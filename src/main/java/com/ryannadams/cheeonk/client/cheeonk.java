package com.ryannadams.cheeonk.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.ryannadams.cheeonk.client.callback.GotEvent;
import com.ryannadams.cheeonk.client.callback.Registered;
import com.ryannadams.cheeonk.client.callback.Signedin;
import com.ryannadams.cheeonk.client.callback.Signedout;
import com.ryannadams.cheeonk.client.event.SignedinEvent;
import com.ryannadams.cheeonk.client.event.SignedoutEvent;
import com.ryannadams.cheeonk.client.widgets.AuthenticationWidget;
import com.ryannadams.cheeonk.client.widgets.BuddyListWidget;
import com.ryannadams.cheeonk.client.widgets.RegistrationWidget;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.GetEvent;
import com.ryannadams.cheeonk.shared.action.Register;
import com.ryannadams.cheeonk.shared.action.Signin;
import com.ryannadams.cheeonk.shared.action.Signout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class cheeonk implements EntryPoint
{
	private final SimpleEventBus eventBus;
	private final Messages messages;

	private final AuthenticationWidget authenticationWidget;
	private final BuddyListWidget buddyList;
	private final RegistrationWidget registrationWidget;

	private final Logger rootLogger;
	private final DispatchAsync dispatchAsync;

	private Timer chatTimer;

	public cheeonk()
	{
		eventBus = new SimpleEventBus();

		dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

		rootLogger = Logger.getLogger("");
		messages = GWT.create(Messages.class);

		authenticationWidget = new AuthenticationWidget(eventBus);
		buddyList = new BuddyListWidget(eventBus);
		registrationWidget = new RegistrationWidget()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				super.onClick(event);

				dispatchAsync.execute(new Register(registrationWidget.getUsername(), registrationWidget.getPassword()), new Registered()
				{
					@Override
					public void got(boolean isRegistered)
					{
						if (isRegistered)
						{
							// Send email with registration information
						}

					}
				});
			}

		};

		chatTimer = new Timer()
		{
			@Override
			public void run()
			{
				getEvent();
			}
		};
	}

	@Override
	public void onModuleLoad()
	{
		authenticationWidget.addGoClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				if (authenticationWidget.validate())
				{
					ConnectionKey.get().setUsername(authenticationWidget.getUsername());
					ConnectionKey.get().setPassword(authenticationWidget.getPassword());

					dispatchAsync.execute(new Signin(ConnectionKey.get()), new Signedin()
					{
						@Override
						public void got(boolean isSignedin, String connectionId)
						{
							if (isSignedin)
							{
								ConnectionKey.get().setConnectionId(connectionId);

								getEvent();

								eventBus.fireEvent(new SignedinEvent());
							}
						}
					});
				}
			}
		});

		authenticationWidget.addSignoutClickHandler(new ClickHandler()
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
							ConnectionKey.get().reset();
							eventBus.fireEvent(new SignedoutEvent());
						}

					}
				});

			}
		});

		RootPanel.get("banner").add(new Image(ImageResources.INSTANCE.getBanner()));
		RootPanel.get("tab").add(new Image(ImageResources.INSTANCE.getTab()));
		RootPanel.get("bannerSignin").add(authenticationWidget);
		RootPanel.get("buddyListContainer").add(buddyList);
	}

	public void getEvent()
	{
		rootLogger.log(Level.FINER, "Waiting for incoming events.");

		dispatchAsync.execute(new GetEvent(ConnectionKey.get()), new GotEvent()
		{
			@Override
			public void got(GwtEvent[] events)
			{
				for (GwtEvent event : events)
				{
					eventBus.fireEvent(event);
				}

				chatTimer.schedule(1);
			}

		});
	}
}
