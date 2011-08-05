package com.cheeonk.client;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.cheeonk.client.callback.GotEvent;
import com.cheeonk.client.callback.Registered;
import com.cheeonk.client.event.ChatCreatedEvent;
import com.cheeonk.client.event.MessageSentEvent;
import com.cheeonk.client.event.SignedinEvent;
import com.cheeonk.client.event.SignedoutEvent;
import com.cheeonk.client.handler.AuthenticationEventHandler;
import com.cheeonk.client.handler.ChatEventHandler;
import com.cheeonk.client.handler.MessageEventHandler;
import com.cheeonk.client.widgets.BuddyListWidget;
import com.cheeonk.client.widgets.PresenceWidget;
import com.cheeonk.client.widgets.RegistrationWidget;
import com.cheeonk.client.widgets.authentication.AuthenticationWidget;
import com.cheeonk.client.widgets.chat.ChatPopupWidget;
import com.cheeonk.client.widgets.chat.IChatWidget;
import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.action.GetEvent;
import com.cheeonk.shared.action.Register;
import com.cheeonk.shared.buddy.JabberId;
import com.cheeonk.shared.event.MessageReceivedEvent;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Cheeonk implements EntryPoint, AuthenticationEventHandler, MessageEventHandler, ChatEventHandler, CloseHandler<Window>
{
	private final DispatchAsync dispatchAsync;
	private final SimpleEventBus eventBus;
	// private final Messages messages;
	private final Logger rootLogger;
	private final DockLayoutPanel rootPanel;

	private final Map<JabberId, IChatWidget> chats;
	private final HorizontalPanel chatPanel;

	private final AuthenticationWidget authenticationWidget;
	private final RegistrationWidget registrationWidget;

	private final VerticalPanel headerPanel;
	private final VerticalPanel westPanel;
	private final VerticalPanel eastPanel;
	private final VerticalPanel centerPanel;
	private final VerticalPanel footerPanel;

	private final Timer chatTimer;

	public Cheeonk()
	{
		this.dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());
		// this.messages = GWT.create(Messages.class);
		this.eventBus = new SimpleEventBus();
		this.eventBus.addHandler(SignedinEvent.TYPE, this);
		this.eventBus.addHandler(SignedoutEvent.TYPE, this);
		this.eventBus.addHandler(MessageReceivedEvent.TYPE, this);
		this.eventBus.addHandler(ChatCreatedEvent.TYPE, this);

		this.rootLogger = Logger.getLogger("");

		this.rootPanel = new DockLayoutPanel(Unit.PX);
		this.rootPanel.setStyleName("root");

		this.authenticationWidget = new AuthenticationWidget(eventBus);
		// this.signinWidget = new SigninWidget(eventBus);
		// this.signinWidget.setStyleName("signinPanelFull");

		this.headerPanel = new VerticalPanel();
		this.headerPanel.setStyleName("header");
		this.headerPanel.add(authenticationWidget);
		this.headerPanel.add(new Image(ImageResources.INSTANCE.getBanner()));

		this.westPanel = new VerticalPanel();
		this.westPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		this.westPanel.setStyleName("west");

		this.eastPanel = new VerticalPanel();
		this.eastPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		this.eastPanel.setStyleName("east");

		this.centerPanel = new VerticalPanel();
		this.centerPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		this.centerPanel.setStyleName("center");

		this.footerPanel = new VerticalPanel();
		this.footerPanel.setStyleName("footer");

		HTML copyright = new HTML("Cheeonk &#169; 2011 - All Rights Reserved.");
		copyright.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.footerPanel.add(copyright);

		chatPanel = new HorizontalPanel();
		chatPanel.setStyleName("chatPanel");

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

		chats = new HashMap<JabberId, IChatWidget>();

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
							centerPanel.clear();
							centerPanel.add(new HTML("Registration Complete."));
						}

					}
				});
			}

		};
		registrationWidget.setStyleName("registrationWidget");

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
		rootPanel.addNorth(headerPanel, 100);
		rootPanel.addSouth(footerPanel, 100);
		rootPanel.addWest(westPanel, 250);
		rootPanel.addEast(eastPanel, 300);
		rootPanel.add(centerPanel);

		eastPanel.add(new HTML("Blank Panel"));
		centerPanel.add(new HTML("Register Now"));
		centerPanel.add(registrationWidget);

		RootLayoutPanel.get().add(rootPanel);
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
		final JabberId key = event.getMessage().getFrom();

		if (!chats.containsKey(key))
		{
			ChatPopupWidget chatPlaceHolder = new ChatPopupWidget(eventBus, key)
			{
				@Override
				public void onClose()
				{
					chatPanel.remove(this);
					chats.remove(key);
				}
			};

			chatPanel.add(chatPlaceHolder);
			chats.put(key, chatPlaceHolder);
		}

		IChatWidget chatContainer = chats.get(key);
		chatContainer.addCheeonk(event.getMessage());
	}

	@Override
	public void onMessageSent(MessageSentEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onChatCreated(ChatCreatedEvent event)
	{
		final JabberId key = event.getBuddy().getJabberId();

		if (!chats.containsKey(key))
		{
			ChatPopupWidget chatPlaceHolder = new ChatPopupWidget(eventBus, key)
			{
				@Override
				public void onClose()
				{
					chatPanel.remove(this);
					chats.remove(key);
				}
			};

			chatPanel.add(chatPlaceHolder);
			chats.put(key, chatPlaceHolder);
		}
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
		VerticalPanel buddyListPanel = new VerticalPanel();
		buddyListPanel.setStyleName("buddyListContainer");
		buddyListPanel.add(new PresenceWidget(eventBus, event.getJabberId()));
		buddyListPanel.add(new BuddyListWidget(eventBus));

		westPanel.clear();
		westPanel.add(buddyListPanel);

		footerPanel.insert(chatPanel, 0);

		ConnectionKey.get().setConnectionId(event.getConnectionId());

		getEvent();
	}

	@Override
	public void onSignedout(SignedoutEvent event)
	{
		westPanel.clear();
		chatPanel.clear();
		chats.clear();
		ConnectionKey.get().reset();
	}
}
