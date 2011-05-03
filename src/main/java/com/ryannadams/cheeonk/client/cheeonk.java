package com.ryannadams.cheeonk.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.services.ChatService;
import com.ryannadams.cheeonk.client.services.ChatServiceAsync;
import com.ryannadams.cheeonk.client.widgets.AuthenticationWidget;
import com.ryannadams.cheeonk.client.widgets.ChatWidget;
import com.ryannadams.cheeonk.client.widgets.HerdWidget;
import com.ryannadams.cheeonk.client.widgets.RegistrationWidget;
import com.ryannadams.cheeonk.shared.ChatServerKey;
import com.ryannadams.cheeonk.shared.buddy.ClientBuddy;
import com.ryannadams.cheeonk.shared.chat.ClientChat;
import com.ryannadams.cheeonk.shared.message.ClientMessage;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class cheeonk implements EntryPoint
{
	private final int POLLING_INTERVAL = 1000;

	private final ChatServiceAsync chatService;
	// private final Messages messages;
	private final ChatServerKey key;
	private final Label errorLabel;
	private final AuthenticationWidget authenticationWidget;
	private final HerdWidget buddyList;
	private final RegistrationWidget registrationWidget;

	private Timer pollBuddyUpdates;
	private Timer pollChats;

	// private final Hashmap<String, ChatWidget>chats;
	private final Logger rootLogger;

	public cheeonk()
	{
		rootLogger = Logger.getLogger("");

		chatService = GWT.create(ChatService.class);
		// messages = GWT.create(Messages.class);
		key = ChatServerKey.getCheeonkConnectionKey();
		authenticationWidget = new AuthenticationWidget();
		errorLabel = new Label();
		buddyList = new HerdWidget();
		registrationWidget = new RegistrationWidget()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				super.onClick(event);

				chatService.register(registrationWidget.getUsername(), registrationWidget.getPassword(), new AsyncCallback<Boolean>()
				{

					@Override
					public void onFailure(Throwable caught)
					{
						rootLogger.log(Level.SEVERE, "Registration Failed");

					}

					@Override
					public void onSuccess(Boolean result)
					{
						errorLabel.setText("Registration Successful");

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

					chatService.login(key, new AsyncCallback<String>()
					{

						@Override
						public void onFailure(Throwable caught)
						{
							rootLogger.log(Level.SEVERE, "Login Unsuccessful.  Please Check your Username and Password.");
						}

						@Override
						public void onSuccess(String result)
						{
							if (result != null)
							{
								// connectionKey.setConnectionID(result);
								authenticationWidget.signedIn();

								chatService.getBuddyList(key, new AsyncCallback<ClientBuddy[]>()
								{

									@Override
									public void onFailure(Throwable caught)
									{
										rootLogger.log(Level.SEVERE, "Failed to get BuddyList");
									}

									@Override
									public void onSuccess(ClientBuddy[] result)
									{
										for (final ClientBuddy buddy : result)
										{
											rootLogger.log(Level.FINE, buddy.getName() + " has been added");
											buddyList.addBuddy(buddy, new ClickHandler()
											{
												@Override
												public void onClick(ClickEvent event)
												{
													chatService.createChat(key, buddy.getJID(), new AsyncCallback<ClientChat>()
													{

														@Override
														public void onFailure(Throwable caught)
														{
															rootLogger.log(Level.SEVERE, "Failed to Add Buddy");
														}

														@Override
														public void onSuccess(final ClientChat chat)
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
													});

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

										chatService.getIncomingChats(key, new AsyncCallback<ClientChat[]>()
										{
											@Override
											public void onFailure(Throwable caught)
											{
												rootLogger.log(Level.SEVERE, "Failed to get Incoming Chats");
											}

											@Override
											public void onSuccess(ClientChat[] chats)
											{
												for (final ClientChat chat : chats)
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

								pollChats.scheduleRepeating(POLLING_INTERVAL);

								pollBuddyUpdates = new Timer()
								{
									@Override
									public void run()
									{
										rootLogger.log(Level.FINER, "Polling for Buddy Updates");

										chatService.getBuddyListUpdates(key, new AsyncCallback<ClientBuddy[]>()
										{

											@Override
											public void onFailure(Throwable caught)
											{
												// TODO Auto-generated method
												// stub

											}

											@Override
											public void onSuccess(ClientBuddy[] result)
											{
												for (ClientBuddy buddy : result)
												{
													if (buddy.isAvailable())
													{
														rootLogger.log(Level.FINE, buddy.getName() + "is available");
													}
													else
													{
														rootLogger.log(Level.FINE, buddy.getName() + "is not available");
													}

												}

											}
										});

									}

								};

								pollBuddyUpdates.scheduleRepeating(POLLING_INTERVAL * 3);

								RootPanel.get("buddyListContainer").add(buddyList);

							}
							else
							{
								errorLabel.setText("login failed");
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

				pollChats.cancel();
				pollBuddyUpdates.cancel();

				buddyList.clearBuddyList();

				chatService.logout(key, new AsyncCallback<Boolean>()
				{

					@Override
					public void onFailure(Throwable caught)
					{
						rootLogger.log(Level.SEVERE, "Failed to Logout");
					}

					@Override
					public void onSuccess(Boolean result)
					{
						if (result)
						{
							authenticationWidget.signedOff();

						}
						else
						{
							rootLogger.log(Level.SEVERE, "Failed to Logout");
						}

					}

				});

			}
		});

		RootPanel.get("banner").add(new Image(ImageResources.INSTANCE.getBanner()));
		RootPanel.get("bannerSignin").add(authenticationWidget);
		RootPanel.get("errorLabelContainer").add(errorLabel);
	}

	private ChatWidget getChatWidget(final ChatServerKey key, final ClientChat chat)
	{
		final ChatWidget chatWidget = new ChatWidget();

		chatWidget.addKeyPressHandler(new KeyPressHandler()
		{
			@Override
			public void onKeyPress(KeyPressEvent event)
			{
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				{
					chatService.sendMessage(key, chat, chatWidget.getMessageAreaText(), new AsyncCallback<Void>()
					{

						@Override
						public void onFailure(Throwable caught)
						{
							rootLogger.log(Level.SEVERE, "Failed to Send Message");
						}

						@Override
						public void onSuccess(Void result)
						{
							chatWidget.resetMessageArea();
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
				rootLogger.log(Level.FINER, "Polling for Incoming Messages with " + chat.getParticipant());

				chatService.getMessages(key, chat, new AsyncCallback<ClientMessage[]>()
				{
					@Override
					public void onFailure(Throwable caught)
					{
						rootLogger.log(Level.SEVERE, "Failed to get Message");
					}

					@Override
					public void onSuccess(ClientMessage[] messages)
					{
						for (ClientMessage message : messages)
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
