package com.ryannadams.cheeonk.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.ryannadams.cheeonk.client.chat.IBuddy;
import com.ryannadams.cheeonk.client.i18n.Messages;
import com.ryannadams.cheeonk.client.services.ChatService;
import com.ryannadams.cheeonk.client.services.ChatServiceAsync;
import com.ryannadams.cheeonk.client.widgets.BuddyList;
import com.ryannadams.cheeonk.client.widgets.LoginWidget;
import com.ryannadams.cheeonk.client.widgets.LogoutWidget;
import com.ryannadams.cheeonk.client.widgets.RegistrationWidget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class cheeonk implements EntryPoint
{
	private final ChatServiceAsync chatService = GWT.create(ChatService.class);

	private final Messages messages = GWT.create(Messages.class);

	private LoginWidget login;
	private LogoutWidget logout;
	private BuddyList buddyList;

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad()
	{
		login = new LoginWidget();
		logout = new LogoutWidget();

		buddyList = new BuddyList()
		{
			@Override
			public void onClick(ClickEvent event)
			{

			}

		};

		final Label errorLabel = new Label();

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

				chatService.logout(new AsyncCallback<Boolean>()
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

		login.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				chatService.login(login.getUsername(), login.getPassword(),
						new AsyncCallback<Boolean>()
						{

							@Override
							public void onFailure(Throwable caught)
							{
								errorLabel.setText("didn't work");
								// got to log login
							}

							@Override
							public void onSuccess(Boolean result)
							{
								if (result)
								{
									RootPanel.get("loginContainer").remove(
											login);
									RootPanel.get("loginContainer").add(logout);
									logout.setUserName(login.getUsername());

									chatService
											.getBuddyList(new AsyncCallback<IBuddy[]>()
											{

												@Override
												public void onFailure(
														Throwable caught)
												{
													// TODO Auto-generated
													// method stub

												}

												@Override
												public void onSuccess(
														IBuddy[] result)
												{
													buddyList
															.setBuddyList(result);

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

		RootPanel.get("banner").add(
				new Image(ImageResources.INSTANCE.getBanner()));

		RootPanel.get("loginContainer").add(login);

		RootPanel.get("buddyListContainer").add(buddyList);

		RootPanel.get("registrationFormContainer")
				.add(new RegistrationWidget());

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("errorLabelContainer").add(errorLabel);

	}
}
