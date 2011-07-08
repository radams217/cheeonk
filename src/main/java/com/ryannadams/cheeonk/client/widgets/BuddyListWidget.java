package com.ryannadams.cheeonk.client.widgets;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.callback.AddedBuddy;
import com.ryannadams.cheeonk.client.event.RemoveBuddyEvent;
import com.ryannadams.cheeonk.client.event.SignedinEvent;
import com.ryannadams.cheeonk.client.event.SignedoutEvent;
import com.ryannadams.cheeonk.client.handler.AuthenticationEventHandler;
import com.ryannadams.cheeonk.client.handler.BuddyEventHandler;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.AddBuddy;
import com.ryannadams.cheeonk.shared.buddy.CheeonkBuddy;
import com.ryannadams.cheeonk.shared.buddy.JabberId;
import com.ryannadams.cheeonk.shared.event.AddBuddyEvent;
import com.ryannadams.cheeonk.shared.event.PresenceChangeEvent;

public class BuddyListWidget extends Composite implements AuthenticationEventHandler, BuddyEventHandler
{
	private final VerticalPanel panel;
	private final Button addButton;

	private final DispatchAsync dispatchAsync;

	private final SimpleEventBus eventBus;

	public BuddyListWidget(final SimpleEventBus eventBus)
	{
		this.eventBus = eventBus;

		this.eventBus.addHandler(SignedinEvent.TYPE, this);
		this.eventBus.addHandler(SignedoutEvent.TYPE, this);
		this.eventBus.addHandler(AddBuddyEvent.TYPE, this);
		this.eventBus.addHandler(RemoveBuddyEvent.TYPE, this);

		dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

		panel = new VerticalPanel();
		panel.addStyleName("buddyListWidget");

		addButton = new Button("Add Buddy");
		addButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				final PopupPanel popupPanel = new PopupPanel(true);

				popupPanel.setPopupPositionAndShow(new PopupPanel.PositionCallback()
				{
					@Override
					public void setPosition(int offsetWidth, int offsetHeight)
					{
						int left = getAbsoluteLeft();
						int top = addButton.getAbsoluteTop() + addButton.getOffsetHeight();
						popupPanel.setPopupPosition(left, top);
					}
				});

				VerticalPanel panel = new VerticalPanel();

				final TextBox jabberId = new TextBox();
				final TextBox buddyName = new TextBox();

				HorizontalPanel jabberPanel = new HorizontalPanel();
				jabberPanel.add(new HTML("Jabber Id:"));
				jabberPanel.add(jabberId);

				HorizontalPanel buddyNamePanel = new HorizontalPanel();
				buddyNamePanel.add(new HTML("Name:"));
				buddyNamePanel.add(buddyName);

				Button okButton = new Button("ok");

				panel.add(jabberPanel);
				panel.add(buddyNamePanel);
				panel.add(okButton);

				okButton.addClickHandler(new ClickHandler()
				{
					@Override
					public void onClick(ClickEvent event)
					{
						final CheeonkBuddy buddy = new CheeonkBuddy(new JabberId(jabberId.getText()), buddyName.getText());

						dispatchAsync.execute(new AddBuddy(ConnectionKey.get(), buddy), new AddedBuddy()
						{
							@Override
							public void got()
							{
								jabberId.setText("");
								buddyName.setText("");
								eventBus.fireEvent(new AddBuddyEvent(buddy));
							}

						});

						popupPanel.hide();
					}
				});
				popupPanel.add(panel);

				popupPanel.show();

			}
		});

		initWidget(panel);
	}

	private class AddBuddyPopupPanel extends PopupPanel
	{
		public AddBuddyPopupPanel()
		{
			super(true);

			VerticalPanel panel = new VerticalPanel();

			add(panel);
		}

	}

	@Override
	public void onSignedin(SignedinEvent event)
	{
		// Add utility Buttons to the Buddy List to come
		panel.add(addButton);
	}

	@Override
	public void onSignedout(SignedoutEvent event)
	{
		panel.clear();
	}

	@Override
	public void onAddBuddy(AddBuddyEvent event)
	{
		panel.add(new BuddyWidget(eventBus, event.getBuddy()));
	}

	@Override
	public void onRemoveBuddy(RemoveBuddyEvent event)
	{
		// BuddyWidget buddyWidget = new BuddyWidget(eventBus, event.get);
		// panel.remove(buddyWidget);
	}

	@Deprecated
	@Override
	public void onPresenceChange(PresenceChangeEvent event)
	{
		// Event not added to event bus for this object
	}
}
