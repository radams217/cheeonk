package com.ryannadams.cheeonk.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.callback.GotChat;
import com.ryannadams.cheeonk.client.callback.GotMessages;
import com.ryannadams.cheeonk.client.callback.Registered;
import com.ryannadams.cheeonk.client.callback.SentMessage;
import com.ryannadams.cheeonk.client.callback.Signedin;
import com.ryannadams.cheeonk.client.callback.Signedout;
import com.ryannadams.cheeonk.client.event.SignedinEvent;
import com.ryannadams.cheeonk.client.event.SignedoutEvent;
import com.ryannadams.cheeonk.client.widgets.AuthenticationWidget;
import com.ryannadams.cheeonk.client.widgets.BuddyListWidget;
import com.ryannadams.cheeonk.client.widgets.ChatWidget;
import com.ryannadams.cheeonk.client.widgets.RegistrationWidget;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.JabberId;
import com.ryannadams.cheeonk.shared.action.GetChat;
import com.ryannadams.cheeonk.shared.action.GetMessages;
import com.ryannadams.cheeonk.shared.action.Register;
import com.ryannadams.cheeonk.shared.action.SendMessage;
import com.ryannadams.cheeonk.shared.action.Signin;
import com.ryannadams.cheeonk.shared.action.Signout;
import com.ryannadams.cheeonk.shared.chat.CheeonkChat;
import com.ryannadams.cheeonk.shared.message.CheeonkMessage;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class cheeonk implements EntryPoint
{
	private final SimpleEventBus eventBus;

	private final int POLLING_INTERVAL = 1000;

	// private final Messages messages;
	private final ConnectionKey key;
	private final Label errorLabel;
	private final AuthenticationWidget authenticationWidget;
	private final BuddyListWidget buddyList;
	private final RegistrationWidget registrationWidget;

	private Timer pollChats;

	private final Logger rootLogger;

	private final DispatchAsync dispatchAsync;

	public cheeonk()
	{
		eventBus = new SimpleEventBus();

		dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

		rootLogger = Logger.getLogger("");
		// messages = GWT.create(Messages.class);
		key = ConnectionKey.getCheeonkConnectionKey();
		authenticationWidget = new AuthenticationWidget(eventBus);
		errorLabel = new Label();
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
							errorLabel.setText("Registration Successful");
						}

					}
				});
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
					key.setUserName(authenticationWidget.getUsername());
					key.setPassword(authenticationWidget.getPassword());

					dispatchAsync.execute(new Signin(key), new Signedin()
					{
						@Override
						public void got(boolean isSignedin, String connectionId)
						{
							if (isSignedin)
							{
								key.setConnectionID(connectionId);

								pollChats = getChatTimer();
								pollChats.scheduleRepeating(POLLING_INTERVAL);

								// Fire Signed in Event
								eventBus.fireEvent(new SignedinEvent(key));
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
				// TODO Need to cancel message timers

				// pollChats.cancel();
				// pollBuddyUpdates.cancel();

				dispatchAsync.execute(new Signout(key), new Signedout()
				{
					@Override
					public void got(boolean isSignedout)
					{
						if (isSignedout)
						{
							// Fire Signed out Event
							eventBus.fireEvent(new SignedoutEvent());
						}

					}
				});

			}
		});

		RootPanel.get("banner").add(new Image(ImageResources.INSTANCE.getBanner()));
		RootPanel.get("bannerSignin").add(authenticationWidget);
		RootPanel.get("buddyListContainer").add(buddyList);
		RootPanel.get("errorLabelContainer").add(errorLabel);
	}

	private ChatWidget getChatWidget(final ConnectionKey key, final CheeonkChat chat)
	{
		final ChatWidget chatWidget = new ChatWidget(eventBus);

		chatWidget.addKeyPressHandler(new KeyPressHandler()
		{
			@Override
			public void onKeyPress(KeyPressEvent event)
			{
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				{
					dispatchAsync.execute(new SendMessage(key, chat, chatWidget.getMessageAreaText()), new SentMessage()
					{

						@Override
						public void got(boolean isSent)
						{
							if (isSent)
							{
								chatWidget.resetMessageArea();
							}

						}

					});

				}
			}

		});

		chatWidget.setTimer(new Timer()
		{
			@Override
			public void run()
			{
				dispatchAsync.execute(new GetMessages(key, chat), new GotMessages()
				{
					@Override
					public void got(CheeonkMessage[] messages)
					{
						for (CheeonkMessage message : messages)
						{
							chatWidget.addCheeonk(message.getFrom(), message.getBody());
						}

					}
				});

			}
		}, POLLING_INTERVAL);

		return chatWidget;

	}

	public void listenForIncomingChats()
	{

	}

	public Timer getChatTimer()
	{
		return new Timer()
		{
			@Override
			public void run()
			{
				rootLogger.log(Level.FINER, "Polling for Incoming Chats");

				dispatchAsync.execute(new GetChat(key, new JabberId("")), new GotChat()
				{

					@Override
					public void got(CheeonkChat[] chats)
					{
						for (final CheeonkChat chat : chats)
						{
							final DialogBox dialog = new DialogBox(false);
							final ChatWidget chatWidget = getChatWidget(key, chat);

							Button close = new Button("Close");
							dialog.setModal(false);
							VerticalPanel panel = new VerticalPanel();
							panel.add(chatWidget);
							panel.add(close);

							dialog.setText(chat.getParticipant());
							dialog.setWidget(panel);

							close.addClickHandler(new ClickHandler()
							{
								@Override
								public void onClick(ClickEvent event)
								{
									dialog.hide();
									chatWidget.cancelTimer();
								}
							});

							dialog.show();
						}

					}

				});

			}
		};
	}

}
