package com.cheeonk.client.widgets;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.cheeonk.client.callback.AddedBuddy;
import com.cheeonk.client.event.RemoveBuddyEvent;
import com.cheeonk.client.event.SignedinEvent;
import com.cheeonk.client.event.SignedoutEvent;
import com.cheeonk.client.handler.AuthenticationEventHandler;
import com.cheeonk.client.handler.BuddyEventHandler;
import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.action.AddBuddy;
import com.cheeonk.shared.event.AddBuddyEvent;
import com.cheeonk.shared.event.PresenceChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BuddyListWidget extends Composite implements AuthenticationEventHandler, BuddyEventHandler
{
	private final DispatchAsync dispatchAsync;
	private final SimpleEventBus eventBus;
	private final Button addButton;
	private final VerticalPanel panel;

	public BuddyListWidget(final SimpleEventBus eventBus)
	{
		this.eventBus = eventBus;
		this.eventBus.addHandler(SignedinEvent.TYPE, this);
		this.eventBus.addHandler(SignedoutEvent.TYPE, this);
		this.eventBus.addHandler(AddBuddyEvent.TYPE, this);
		this.eventBus.addHandler(RemoveBuddyEvent.TYPE, this);

		this.dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

		panel = new VerticalPanel();
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
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

				AddBuddyWidget addBuddyWidget = new AddBuddyWidget()
				{
					@Override
					public void onClick(ClickEvent event)
					{
						dispatchAsync.execute(new AddBuddy(ConnectionKey.get(), getBuddy()), new AddedBuddy()
						{
							@Override
							public void got()
							{
								clear();
							}

						});

						popupPanel.hide();
					}
				};

				popupPanel.add(addBuddyWidget);

				popupPanel.show();
			}
		});
		panel.add(addButton);
		initWidget(panel);
		setStyleName("buddyListWidget");
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
