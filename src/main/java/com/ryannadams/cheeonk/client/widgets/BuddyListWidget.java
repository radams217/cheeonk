package com.ryannadams.cheeonk.client.widgets;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.event.RemoveBuddyEvent;
import com.ryannadams.cheeonk.client.event.SignedinEvent;
import com.ryannadams.cheeonk.client.event.SignedoutEvent;
import com.ryannadams.cheeonk.client.handler.AuthenticationEventHandler;
import com.ryannadams.cheeonk.client.handler.BuddyEventHandler;
import com.ryannadams.cheeonk.shared.buddy.CheeonkBuddy;
import com.ryannadams.cheeonk.shared.buddy.IBuddy;
import com.ryannadams.cheeonk.shared.buddy.JabberId;
import com.ryannadams.cheeonk.shared.event.AddBuddyEvent;
import com.ryannadams.cheeonk.shared.event.PresenceChangeEvent;

public class BuddyListWidget extends Composite implements AuthenticationEventHandler, BuddyEventHandler
{
	private final VerticalPanel panel;

	private final Button addButton;
	private final TextBox buddyName;
	private final Button okButton;

	private final DispatchAsync dispatchAsync;

	private final SimpleEventBus eventBus;

	public BuddyListWidget(final SimpleEventBus eventBus)
	{
		this.eventBus = eventBus;

		this.eventBus.addHandler(SignedinEvent.TYPE, this);
		this.eventBus.addHandler(SignedoutEvent.TYPE, this);
		this.eventBus.addHandler(AddBuddyEvent.TYPE, this);
		this.eventBus.addHandler(RemoveBuddyEvent.TYPE, this);
		this.eventBus.addHandler(PresenceChangeEvent.TYPE, this);

		dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

		panel = new VerticalPanel();
		panel.addStyleName("buddyListWidget");

		buddyName = new TextBox();
		okButton = new Button("ok");

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

				HorizontalPanel panel = new HorizontalPanel();
				panel.add(buddyName);
				panel.add(okButton);

				okButton.addClickHandler(new ClickHandler()
				{
					@Override
					public void onClick(ClickEvent event)
					{
						CheeonkBuddy buddy = new CheeonkBuddy(new JabberId(buddyName.getText()), buddyName.getText());

						eventBus.fireEvent(new AddBuddyEvent(buddy));

						popupPanel.hide();
					}
				});
				popupPanel.add(panel);

				popupPanel.show();

			}
		});

		initWidget(panel);
	}

	public void addBuddy(final IBuddy buddy, ClickHandler clickHandler)
	{
		BuddyWidget buddyWidget = new BuddyWidget(eventBus, buddy);
		buddyWidget.addClickHandler(clickHandler);

		panel.add(buddyWidget);
	}

	public void removeBuddy(IBuddy buddy)
	{
		BuddyWidget buddyWidget = new BuddyWidget(eventBus, buddy);

		panel.remove(buddyWidget);
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
		final IBuddy buddy = event.getBuddy();

		addBuddy(buddy, new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				final ChatWidgetDialog chatWidget = new ChatWidgetDialog(eventBus, buddy);
				chatWidget.setText(buddy.getJabberId().toString());
				chatWidget.show();
			}

		});

	}

	@Override
	public void onRemoveBuddy(RemoveBuddyEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onPresenceChange(PresenceChangeEvent event)
	{
		// TODO Auto-generated method stub

	}
}
