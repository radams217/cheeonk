package com.ryannadams.cheeonk.client;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.ryannadams.cheeonk.client.callback.GotEvent;
import com.ryannadams.cheeonk.client.callback.Registered;
import com.ryannadams.cheeonk.client.event.ChatCreatedEvent;
import com.ryannadams.cheeonk.client.event.MessageSentEvent;
import com.ryannadams.cheeonk.client.event.SignedinEvent;
import com.ryannadams.cheeonk.client.event.SignedoutEvent;
import com.ryannadams.cheeonk.client.handler.AuthenticationEventHandler;
import com.ryannadams.cheeonk.client.handler.ChatEventHandler;
import com.ryannadams.cheeonk.client.handler.MessageEventHandler;
import com.ryannadams.cheeonk.client.widgets.AuthenticationWidget;
import com.ryannadams.cheeonk.client.widgets.BuddyListWidget;
import com.ryannadams.cheeonk.client.widgets.PresenceWidget;
import com.ryannadams.cheeonk.client.widgets.RegistrationWidget;
import com.ryannadams.cheeonk.client.widgets.chat.ChatWidgetContainer;
import com.ryannadams.cheeonk.client.widgets.chat.ChatWidgetDialog;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.GetEvent;
import com.ryannadams.cheeonk.shared.action.Register;
import com.ryannadams.cheeonk.shared.buddy.JabberId;
import com.ryannadams.cheeonk.shared.event.MessageReceivedEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class cheeonk implements EntryPoint, AuthenticationEventHandler, MessageEventHandler, ChatEventHandler, CloseHandler<Window>
{
	private final SimpleEventBus eventBus;
	// private final Messages messages;

	private final AuthenticationWidget authenticationWidget;
	private final RegistrationWidget registrationWidget;

	private final Logger rootLogger;
	private final DispatchAsync dispatchAsync;

	private final Map<JabberId, ChatWidgetContainer> chats;

	private Timer chatTimer;

	public cheeonk()
	{
		Window.addWindowClosingHandler(new Window.ClosingHandler()
		{
			@Override
			public void onWindowClosing(Window.ClosingEvent closingEvent)
			{
				if (ConnectionKey.get().getConnectionId() != null)
				{
					closingEvent.setMessage("All conversations will be lost.  Do you really want to leave the page?");
				}
			}
		});

		Window.addCloseHandler(this);

		eventBus = new SimpleEventBus();
		eventBus.addHandler(SignedinEvent.TYPE, this);
		eventBus.addHandler(SignedoutEvent.TYPE, this);
		eventBus.addHandler(MessageReceivedEvent.TYPE, this);
		eventBus.addHandler(ChatCreatedEvent.TYPE, this);

		chats = new HashMap<JabberId, ChatWidgetContainer>();

		dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

		rootLogger = Logger.getLogger("");
		// messages = GWT.create(Messages.class);

		authenticationWidget = new AuthenticationWidget(eventBus);
		registrationWidget = new RegistrationWidget()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				super.onClick(event);

				dispatchAsync.execute(new Register(registrationWidget.getUsername(), registrationWidget.getPassword(), registrationWidget.getName(),
						registrationWidget.getEmail()), new Registered()
				{
					@Override
					public void got(boolean isRegistered)
					{
						if (isRegistered)
						{
							RootPanel.get("registerContainer").clear();
							RootPanel.get("registerContainer").add(new HTML("Registration Complete.  Log in Above."));
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
		RootPanel.get("banner").add(new Image(ImageResources.INSTANCE.getBanner()));
		RootPanel.get("registerContainer").add(registrationWidget);
		RootPanel.get("authenticationWidget").add(authenticationWidget);
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

	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		JabberId key = event.getMessage().getFrom();

		if (!chats.containsKey(key))
		{
			chats.put(key, new ChatWidgetDialog(eventBus, key));
		}

		ChatWidgetContainer chatContainer = chats.get(key);
		chatContainer.addCheeonk(event.getMessage());
		chatContainer.show();
	}

	@Override
	public void onMessageSent(MessageSentEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onChatCreated(ChatCreatedEvent event)
	{
		JabberId key = event.getBuddy().getJabberId();

		if (!chats.containsKey(key))
		{
			chats.put(key, new ChatWidgetDialog(eventBus, key));
		}

		ChatWidgetContainer chatContainer = chats.get(key);
		chatContainer.show();
	}

	@Override
	public void onClose(CloseEvent<Window> event)
	{
		// if (ConnectionKey.get().getConnectionId() != null)
		// {
		// dispatchAsync.execute(new Signout(ConnectionKey.get()), new
		// Signedout()
		// {
		// @Override
		// public void got(boolean isSignedout)
		// {
		//
		// }
		// });
		// }
	}

	@Override
	public void onSignedin(SignedinEvent event)
	{
		RootPanel.get("buddyListContainer").add(new PresenceWidget(eventBus, event.getJabberId()));
		RootPanel.get("buddyListContainer").add(new BuddyListWidget(eventBus));

		ConnectionKey.get().setConnectionId(event.getConnectionId());

		getEvent();
	}

	@Override
	public void onSignedout(SignedoutEvent event)
	{
		RootPanel.get("buddyListContainer").clear();

		ConnectionKey.get().reset();

	}
}
