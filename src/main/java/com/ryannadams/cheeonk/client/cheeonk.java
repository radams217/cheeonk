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
import com.ryannadams.cheeonk.client.widgets.AuthenticationWidget;
import com.ryannadams.cheeonk.client.widgets.BuddyList;
import com.ryannadams.cheeonk.client.widgets.BuddyWidget;
import com.ryannadams.cheeonk.client.widgets.ChatPanel;
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
	private final BuddyList buddyList;

	private Timer pollBuddyUpdates;
	private Timer pollChats;

	public cheeonk()
	{
		chatService = GWT.create(ChatService.class);
		messages = GWT.create(Messages.class);
		key = ChatServerKey.getCheeonkConnectionKey();
		authenticationWidget = new AuthenticationWidget();
		errorLabel = new Label();
		buddyList = new BuddyList();
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
							final ChatPanel chatPanel = new ChatPanel(chat.getThreadID());
							chatPanel.addKeyPressHandler(new KeyPressHandler()
							{

								@Override
								public void onKeyPress(KeyPressEvent event)
								{
									if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
									{
										chatService.sendMessage(key, chat, chatPanel.getMessageText(), new AsyncCallback<Void>()
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

							Timer timer = pollMessages(chatPanel, chat);
							timer.scheduleRepeating(POLLING_INTERVAL);

							chatPanel.show();
						}

					}

				});

			}
		};

		pollChats.scheduleRepeating(POLLING_INTERVAL);

	}

	private Timer pollMessages(final ChatPanel chatPanel, final ClientChat chat)
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
							chatPanel.addChatMessage(message.getFrom(), message.getBody());
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
											buddyList.addBuddy(new BuddyWidget(buddy)
											{
												@Override
												public void onClick(ClickEvent event)
												{
													createChatPanel(buddy.getName()).show();
												}

											});
										}
									}
								});

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
		RootPanel.get("buddyListContainer").add(buddyList);
		RootPanel.get("errorLabelContainer").add(errorLabel);

	}

	public ChatPanel createChatPanel(String to)
	{
		final ChatPanel chatPanel = new ChatPanel(to + "@ryannadams.com");

		chatService.createChat(key, to + "@ryannadams.com", new AsyncCallback<ClientChat>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(final ClientChat chat)
			{
				final Timer timer = pollMessages(chatPanel, chat);
				timer.scheduleRepeating(POLLING_INTERVAL);

				chatPanel.addClickHandler(new ClickHandler()
				{
					@Override
					public void onClick(ClickEvent event)
					{
						timer.cancel();
					}
				});

				chatPanel.addKeyPressHandler(new KeyPressHandler()
				{
					@Override
					public void onKeyPress(KeyPressEvent event)
					{
						if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
						{
							chatService.sendMessage(key, chat, chatPanel.getMessageText(), new AsyncCallback<Void>()
							{

								@Override
								public void onFailure(Throwable caught)
								{

								}

								@Override
								public void onSuccess(Void result)
								{
									chatPanel.clearMessageText();

								}
							});

						}

					}
				});

			}
		});

		return chatPanel;

	}
}
