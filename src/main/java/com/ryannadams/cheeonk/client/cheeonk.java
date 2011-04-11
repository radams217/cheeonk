package com.ryannadams.cheeonk.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.ryannadams.cheeonk.client.chat.ClientBuddy;
import com.ryannadams.cheeonk.client.chat.ClientChat;
import com.ryannadams.cheeonk.client.chat.ClientMessage;
import com.ryannadams.cheeonk.client.i18n.Messages;
import com.ryannadams.cheeonk.client.services.ChatService;
import com.ryannadams.cheeonk.client.services.ChatServiceAsync;
import com.ryannadams.cheeonk.client.widgets.BuddyList;
import com.ryannadams.cheeonk.client.widgets.ChatPanel;
import com.ryannadams.cheeonk.client.widgets.LoginWidget;
import com.ryannadams.cheeonk.client.widgets.LogoutWidget;
import com.ryannadams.cheeonk.client.widgets.RegistrationWidget;
import com.ryannadams.cheeonk.shared.chat.ConnectionKey;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class cheeonk implements EntryPoint
{
	private final int POLLING_INTERVAL = 5000;
	private final ChatServiceAsync chatService = GWT.create(ChatService.class);

	private final Messages messages = GWT.create(Messages.class);

	private LoginWidget login;
	private LogoutWidget logout;
	private BuddyList buddyList;

	private Timer pollBuddyUpdates;
	private Timer pollChats;

	private final ConnectionKey connectionKey = ConnectionKey
			.getCheeonkConnectionKey();

	private void startPollingChats()
	{
		pollChats = new Timer()
		{
			@Override
			public void run()
			{
				chatService.getIncomingChats(connectionKey,
						new AsyncCallback<ClientChat[]>()
						{

							@Override
							public void onFailure(Throwable caught)
							{
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(ClientChat[] chats)
							{

								for (final ClientChat chat : chats)
								{
									final ChatPanel chatPanel = new ChatPanel(
											chat.getParticipant());
									chatPanel
											.addKeyPressHandler(new KeyPressHandler()
											{

												@Override
												public void onKeyPress(
														KeyPressEvent event)
												{
													if (KeyCodes.KEY_ENTER == event
															.getNativeEvent()
															.getKeyCode())
													{
														chatService
																.sendMessage(
																		connectionKey,
																		chat,
																		chatPanel
																				.getMessageText(),
																		new AsyncCallback<Void>()
																		{

																			@Override
																			public void onFailure(
																					Throwable caught)
																			{
																				// TODO
																				// Auto-generated
																				// method
																				// stub

																			}

																			@Override
																			public void onSuccess(
																					Void result)
																			{
																				// TODO
																				// Auto-generated
																				// method
																				// stub

																			}
																		});
													}

												}
											});

									Timer pollMessages = new Timer()
									{
										@Override
										public void run()
										{
											chatService
													.getMessages(
															connectionKey,
															chat,
															new AsyncCallback<ClientMessage[]>()
															{

																@Override
																public void onFailure(
																		Throwable caught)
																{
																	// TODO
																	// Auto-generated
																	// method
																	// stub

																}

																@Override
																public void onSuccess(
																		ClientMessage[] messages)
																{
																	for (ClientMessage message : messages)
																	{
																		chatPanel
																				.addChatMessage(
																						message.getFrom(),
																						message.getBody());
																	}

																}
															});

										}

									};

									pollMessages
											.scheduleRepeating(POLLING_INTERVAL);

									chatPanel.show();
								}

							}

						});

			}
		};

		pollChats.scheduleRepeating(POLLING_INTERVAL);

	}

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad()
	{
		// Temp for now to display errors
		final Label errorLabel = new Label();

		login = new LoginWidget();
		logout = new LogoutWidget();

		login.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				connectionKey.setUserName(login.getUsername());
				connectionKey.setPassword(login.getPassword());

				chatService.login(connectionKey, new AsyncCallback<String>()
				{

					@Override
					public void onFailure(Throwable caught)
					{
						errorLabel
								.setText("Login Unsuccessful.  Please Check your UserName and Password.");
					}

					@Override
					public void onSuccess(String result)
					{
						if (result != null)
						{
							connectionKey.setConnectionID(result);
							RootPanel.get("loginContainer").remove(login);
							RootPanel.get("loginContainer").add(logout);
							logout.setUserName(login.getUsername());

							startPollingChats();

							chatService.getBuddyList(connectionKey,
									new AsyncCallback<ClientBuddy[]>()
									{

										@Override
										public void onFailure(Throwable caught)
										{
											// TODO Auto-generated
											// method stub

										}

										@Override
										public void onSuccess(
												ClientBuddy[] result)
										{
											buddyList.setBuddyList(result);

										}
									});

						} else
						{
							errorLabel.setText("login failed");
						}
					}

				});
			}
		});

		buddyList = new BuddyList()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				final ChatPanel chatPanel = new ChatPanel(
						"radams217@ryannadams.com");

				chatService.createChat(connectionKey,
						"radams217@ryannadams.com",
						new AsyncCallback<ClientChat>()
						{

							@Override
							public void onFailure(Throwable caught)
							{
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(final ClientChat chat)
							{
								Timer pollMessages = new Timer()
								{
									@Override
									public void run()
									{
										chatService
												.getMessages(
														connectionKey,
														chat,
														new AsyncCallback<ClientMessage[]>()
														{

															@Override
															public void onFailure(
																	Throwable caught)
															{
																// TODO
																// Auto-generated
																// method stub

															}

															@Override
															public void onSuccess(
																	ClientMessage[] messages)
															{
																for (ClientMessage message : messages)
																{
																	chatPanel
																			.addChatMessage(
																					message.getFrom(),
																					message.getBody());
																}

															}
														});

									}

								};

								pollMessages
										.scheduleRepeating(POLLING_INTERVAL);

								chatPanel
										.addKeyPressHandler(new KeyPressHandler()
										{

											@Override
											public void onKeyPress(
													KeyPressEvent event)
											{
												if (KeyCodes.KEY_ENTER == event
														.getNativeEvent()
														.getKeyCode())
												{
													chatService.sendMessage(
															connectionKey,
															chat,
															chatPanel
																	.getMessageText(),
															new AsyncCallback<Void>()
															{

																@Override
																public void onFailure(
																		Throwable caught)
																{
																	// TODO
																	// Auto-generated
																	// method
																	// stub

																}

																@Override
																public void onSuccess(
																		Void result)
																{
																	// TODO
																	// Auto-generated
																	// method
																	// stub

																}
															});

												}

											}
										});

							}
						});

				chatPanel.show();
			}

		};

		// Create the popup dialog box
		// final DialogBox dialogBox = new DialogBox();
		// dialogBox.setText("Remote Procedure Call");
		// dialogBox.setAnimationEnabled(true);
		// We can set the id of a widget by accessing its Element
		// NOTE: closeButton.getElement().setId("closeButton");

		logout.addClickHandler(new ClickHandler()
		{

			@Override
			public void onClick(ClickEvent event)
			{

				chatService.logout(connectionKey, new AsyncCallback<Boolean>()
				{

					@Override
					public void onFailure(Throwable caught)
					{
						errorLabel.setText("didn't work");

					}

					@Override
					public void onSuccess(Boolean result)
					{
						if (result)
						{
							connectionKey.setConnectionID(null);
							RootPanel.get("loginContainer").remove(logout);
							RootPanel.get("loginContainer").add(login);

						} else
						{
							errorLabel.setText("logout failed");
						}

					}

				});

			}
		});

		RootPanel.get("banner").add(
				new Image(ImageResources.INSTANCE.getBanner()));

		RootPanel.get("loginContainer").add(login);

		RootPanel.get("buddyListContainer").add(buddyList);

		RootPanel.get("registrationFormContainer")
				.add(new RegistrationWidget());

		RootPanel.get("errorLabelContainer").add(errorLabel);

	}
}
