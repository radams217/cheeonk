package com.cheeonk.client;

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
import com.cheeonk.client.widgets.ChatTrayWidget;
import com.cheeonk.client.widgets.PresenceWidget;
import com.cheeonk.client.widgets.authentication.AuthenticationWidget;
import com.cheeonk.client.widgets.authentication.SigninWidget;
import com.cheeonk.client.widgets.registration.RegistrationLinkWidget;
import com.cheeonk.client.widgets.registration.RegistrationWidget;
import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.action.GetEvent;
import com.cheeonk.shared.action.Register;
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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
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

	private final ChatTrayWidget chatTrayWidget;

	private final AuthenticationWidget authenticationWidget;
	private final RegistrationLinkWidget registrationLinkWidget;
	private final RegistrationWidget registrationWidget;
	private final SigninWidget signinWidget;

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

		this.chatTrayWidget = new ChatTrayWidget(eventBus);

		this.authenticationWidget = new AuthenticationWidget(eventBus);
		this.signinWidget = new SigninWidget(eventBus);

		this.headerPanel = new VerticalPanel();
		this.headerPanel.setStyleName("header");

		this.westPanel = new VerticalPanel();
		this.westPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		this.westPanel.addStyleName("west");

		this.eastPanel = new VerticalPanel();
		this.eastPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		this.eastPanel.addStyleName("east");

		this.centerPanel = new VerticalPanel();
		this.centerPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		this.centerPanel.addStyleName("center");

		this.footerPanel = new VerticalPanel();
		this.footerPanel.addStyleName("footer");

		final PopupPanel popup = new PopupPanel(true);
		popup.setStyleName("RegistrationWidget-Popup");

		// TODO Move the onclick to the actual widget, call the dispatcher from
		// there and create an onsuccess event
		// TODO fix bug that will occur after registering.
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
							registrationWidget.onSuccess();

							Timer timer = new Timer()
							{
								@Override
								public void run()
								{
									popup.setAnimationEnabled(true);
									popup.hide();

								}
							};

							timer.schedule(3000);
						}

					}
				});
			}

		};

		popup.setGlassEnabled(true);
		popup.add(registrationWidget);

		registrationLinkWidget = new RegistrationLinkWidget()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				popup.show();
				popup.center();
			}
		};

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
		rootPanel.addSouth(footerPanel, 60);
		rootPanel.addWest(westPanel, 500);
		rootPanel.addEast(eastPanel, 300);
		rootPanel.add(centerPanel);

		createMainPage();

		RootLayoutPanel.get().add(rootPanel);
	}

	private void createMainPage()
	{
		clear();

		headerPanel.add(authenticationWidget);
		headerPanel.add(new Image(ImageResources.INSTANCE.getLogo()));

		HTML copyright = new HTML("Cheeonk &#169; 2011 - All Rights Reserved.");
		copyright.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.footerPanel.add(copyright);

		westPanel.add(new HTML("Stay Connected with Friends by Cheeonking the Shit out of them."));
		westPanel.add(new HTML("Join the Herd for up to the minute cheeonks for your friends."));
		westPanel.add(new HTML("Latest Cheeonk:"));

		eastPanel.add(signinWidget);
		eastPanel.add(registrationLinkWidget);

		centerPanel.add(new HTML("test"));
	}

	private void clear()
	{
		headerPanel.clear();
		footerPanel.clear();
		westPanel.clear();
		eastPanel.clear();
		centerPanel.clear();
	}

	private void getEvent()
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

	}

	@Override
	public void onMessageSent(MessageSentEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onChatCreated(ChatCreatedEvent event)
	{

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
		buddyListPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		buddyListPanel.addStyleName("buddyListContainer");
		buddyListPanel.add(new PresenceWidget(eventBus, event.getJabberId()));
		buddyListPanel.add(new BuddyListWidget(eventBus));

		eastPanel.clear();
		westPanel.clear();
		westPanel.add(buddyListPanel);

		footerPanel.add(chatTrayWidget);

		ConnectionKey.get().setConnectionId(event.getConnectionId());

		getEvent();
	}

	@Override
	public void onSignedout(SignedoutEvent event)
	{
		chatTrayWidget.clear();
		ConnectionKey.get().reset();
		chatTimer.cancel();

		createMainPage();
	}
}
