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
import com.ryannadams.cheeonk.client.widgets.AuthenticationWidget;
import com.ryannadams.cheeonk.client.widgets.ChatWidget;
import com.ryannadams.cheeonk.client.widgets.HerdWidget;
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
	private final int POLLING_INTERVAL = 1000;

	// private final Messages messages;
	private final ConnectionKey key;
	private final Label errorLabel;
	private final AuthenticationWidget authenticationWidget;
	private final HerdWidget buddyList;
	private final RegistrationWidget registrationWidget;

	private Timer pollBuddyUpdates;
	private Timer pollChats;

	// private final Hashmap<String, ChatWidget>chats;
	private final Logger rootLogger;

	private final DispatchAsync dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

	public cheeonk()
	{
		rootLogger = Logger.getLogger("");
		// messages = GWT.create(Messages.class);
		key = ConnectionKey.getCheeonkConnectionKey();
		authenticationWidget = new AuthenticationWidget();
		errorLabel = new Label();
		buddyList = new HerdWidget();
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
						public void got(boolean isSignedin)
						{
							if (isSignedin)
							{

							}
						}
					});
				}
			}
		});

		pollChats = new Timer()
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

							Button button = new Button("Close");
							dialog.setModal(false);
							VerticalPanel panel = new VerticalPanel();
							panel.add(chatWidget);
							panel.add(button);

							dialog.setText(chat.getParticipant());
							dialog.setWidget(panel);

							button.addClickHandler(new ClickHandler()
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

		// pollChats.scheduleRepeating(POLLING_INTERVAL);
		//
		// pollBuddyUpdates = new Timer()
		// {
		// @Override
		// public void run()
		// {
		// rootLogger.log(Level.FINER,
		// "Polling for Buddy Updates");
		//
		// chatService.getBuddyListUpdates(key, new
		// AsyncCallback<ClientBuddy[]>()
		// {
		//
		// @Override
		// public void onFailure(Throwable caught)
		// {
		// // TODO Auto-generated method
		// // stub
		//
		// }
		//
		// @Override
		// public void onSuccess(ClientBuddy[] result)
		// {
		// for (ClientBuddy buddy : result)
		// {
		// if (buddy.isAvailable())
		// {
		// rootLogger.log(Level.FINE, buddy.getName() +
		// "is available");
		// buddyList.setBuddyAvailable(buddy);
		// }
		// else
		// {
		// rootLogger.log(Level.FINE, buddy.getName() +
		// "is not available");
		// buddyList.setBuddyUnavailable(buddy);
		// }
		//
		// }
		//
		// }
		// });
		//
		// }
		//
		// };
		//
		// pollBuddyUpdates.scheduleRepeating(POLLING_INTERVAL
		// * 3);

		RootPanel.get("buddyListContainer").add(buddyList);

		authenticationWidget.addSignoutClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				// TODO Need to cancel message timers

				pollChats.cancel();
				pollBuddyUpdates.cancel();

				buddyList.clearBuddyList();

				dispatchAsync.execute(new Signout(key), new Signedout()
				{
					@Override
					public void got(boolean isSignedout)
					{
						if (isSignedout)
						{
							authenticationWidget.signedOff();
						}

					}
				});

			}
		});

		RootPanel.get("banner").add(new Image(ImageResources.INSTANCE.getBanner()));
		RootPanel.get("bannerSignin").add(authenticationWidget);
		RootPanel.get("errorLabelContainer").add(errorLabel);
	}

	private ChatWidget getChatWidget(final ConnectionKey key, final CheeonkChat chat)
	{
		final ChatWidget chatWidget = new ChatWidget();

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
}
