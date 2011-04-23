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
import com.ryannadams.cheeonk.client.i18n.Messages;
import com.ryannadams.cheeonk.client.services.ChatService;
import com.ryannadams.cheeonk.client.services.ChatServiceAsync;
import com.ryannadams.cheeonk.client.widgets.AuthenticationWidget;
import com.ryannadams.cheeonk.client.widgets.CheeonkWidget;
import com.ryannadams.cheeonk.client.widgets.HerdWidget;
import com.ryannadams.cheeonk.client.widgets.RegistrationWidget;
import com.ryannadams.cheeonk.shared.chat.ChatServerKey;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class cheeonk implements EntryPoint
{
	private final int POLLING_INTERVAL = 1000;

	private final String signInContainer = "bannerSignin";

	private final ChatServiceAsync chatService;
	private final Messages messages;
	private final ChatServerKey key;
	private final Label errorLabel;
	private final AuthenticationWidget authenticationWidget;
	private final HerdWidget buddyList;
	private final RegistrationWidget registrationWidget;
	private final VerticalPanel cheeonkTabs;

	private Timer pollBuddyUpdates;
	private Timer pollChats;

	public cheeonk()
	{
		cheeonkTabs = new VerticalPanel();
		chatService = GWT.create(ChatService.class);
		messages = GWT.create(Messages.class);
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

	private void pollChats()
	{
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
							final CheeonkWidget cheeonkWidget = createChatPanel(chat);

							Timer timer = pollMessages(cheeonkWidget, chat);
							timer.scheduleRepeating(POLLING_INTERVAL);

							cheeonkTabs.add(cheeonkWidget);// ,
															// chat.getParticipant());
						}

					}

				});

			}
		};

		pollChats.scheduleRepeating(POLLING_INTERVAL);

	}

	private Timer pollMessages(final CheeonkWidget cheeonkWidget, final ClientChat chat)
	{
		return new Timer()
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
							cheeonkWidget.addCheeonk(message.getFrom(), message.getBody());
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

								pollChats();

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
															// TODO
															// Auto-generated
															// method stub

														}

														@Override
														public void onSuccess(final ClientChat chat)
														{
															final CheeonkWidget cheeonkWidget = createChatPanel(chat);

															final Timer timer = pollMessages(cheeonkWidget, chat);
															timer.scheduleRepeating(POLLING_INTERVAL);

															cheeonkTabs.add(cheeonkWidget);// ,
																							// chat.getParticipant());
														}
													});

												}

											});
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
		RootPanel.get(signInContainer).add(authenticationWidget);

		RootPanel.get("errorLabelContainer").add(errorLabel);
		RootPanel.get("chatContainer").add(cheeonkTabs);

	}

	public CheeonkWidget createChatPanel(final ClientChat chat)
	{
		final CheeonkWidget cheeonkWidget = new CheeonkWidget();
		cheeonkWidget.addKeyPressHandler(new KeyPressHandler()
		{
			@Override
			public void onKeyPress(KeyPressEvent event)
			{
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				{
					chatService.sendMessage(key, chat, cheeonkWidget.getMessage(), new AsyncCallback<Void>()
					{

						@Override
						public void onFailure(Throwable caught)
						{

						}

						@Override
						public void onSuccess(Void result)
						{

						}
					});
				}

			}
		});

		// cheeonkWidget.addClickHandler(new
		// ClickHandler()
		// {
		// @Override
		// public void
		// onClick(ClickEvent
		// event)
		// {
		// timer.cancel();
		// }
		// });

		return cheeonkWidget;
	}
}
