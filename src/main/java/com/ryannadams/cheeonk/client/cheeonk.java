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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.chat.ClientBuddy;
import com.ryannadams.cheeonk.client.chat.ClientChat;
import com.ryannadams.cheeonk.client.chat.ClientMessage;
import com.ryannadams.cheeonk.client.services.ChatService;
import com.ryannadams.cheeonk.client.services.ChatServiceAsync;
import com.ryannadams.cheeonk.client.widgets.AuthenticationWidget;
import com.ryannadams.cheeonk.client.widgets.ChatWidget;
import com.ryannadams.cheeonk.client.widgets.HerdWidget;
import com.ryannadams.cheeonk.client.widgets.RegistrationWidget;
import com.ryannadams.cheeonk.shared.chat.ChatServerKey;

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
	private final VerticalPanel cheeonkTabs;

	private Timer pollBuddyUpdates;
	private Timer pollChats;

	// private final Hashmap<String, ChatWidget>chats;

	public cheeonk()
	{
		cheeonkTabs = new VerticalPanel();
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
						errorLabel.setText("Registration Failed");

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
							errorLabel.setText("Login Unsuccessful.  Please Check your Username and Password.");
						}

						@Override
						public void onSuccess(String result)
						{
							if (result != null)
							{
								// connectionKey.setConnectionID(result);
								authenticationWidget.signedIn();

								pollBuddyUpdates = new Timer()
								{

									@Override
									public void run()
									{
										chatService.getBuddyUpdates(key, new AsyncCallback<ClientBuddy[]>()
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
												// TODO Auto-generated method
												// stub

											}
										});

									}

								};

								pollBuddyUpdates.scheduleRepeating(POLLING_INTERVAL);

								pollChats = new Timer()
								{
									@Override
									public void run()
									{
										chatService.getIncomingChats(key, new AsyncCallback<ClientChat[]>()
										{
											@Override
											public void onFailure(Throwable caught)
											{

											}

											@Override
											public void onSuccess(ClientChat[] chats)
											{
												for (final ClientChat chat : chats)
												{
													cheeonkTabs.add(getChatWidget(key, chat));
												}

											}

										});

									}
								};

								pollChats.scheduleRepeating(POLLING_INTERVAL);

								chatService.getBuddyList(key, new AsyncCallback<ClientBuddy[]>()
								{

									@Override
									public void onFailure(Throwable caught)
									{
										// TODO Auto-generated
										// method stub

									}

									@Override
									public void onSuccess(ClientBuddy[] result)
									{
										for (final ClientBuddy buddy : result)
										{
											if (buddy.isAvailable())
											{
												buddyList.addBuddy(buddy, new ClickHandler()
												{
													@Override
													public void onClick(ClickEvent event)
													{
														chatService.createChat(key, buddy.getName() + "@ryannadams.com", new AsyncCallback<ClientChat>()
														{

															@Override
															public void onFailure(Throwable caught)
															{

															}

															@Override
															public void onSuccess(final ClientChat chat)
															{
																cheeonkTabs.add(getChatWidget(key, chat));
															}
														});

													}

												});
											}
										}
									}
								});

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

				buddyList.clearBuddyList();

				chatService.logout(key, new AsyncCallback<Boolean>()
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
							authenticationWidget.signedOff();

						}
						else
						{
							errorLabel.setText("logout failed");
						}

					}

				});

			}
		});

		RootPanel.get("banner").add(new Image(ImageResources.INSTANCE.getBanner()));
		RootPanel.get("bannerSignin").add(authenticationWidget);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		RootPanel.get("chatContainer").add(cheeonkTabs);
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
							// Keep text in area and highlight it or something

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
				chatService.getMessages(key, chat, new AsyncCallback<ClientMessage[]>()
				{
					@Override
					public void onFailure(Throwable caught)
					{
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
