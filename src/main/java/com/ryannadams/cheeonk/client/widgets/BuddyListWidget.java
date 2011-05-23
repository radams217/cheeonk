package com.ryannadams.cheeonk.client.widgets;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.callback.CreatedChat;
import com.ryannadams.cheeonk.client.callback.GotBuddyList;
import com.ryannadams.cheeonk.client.event.AddBuddyEvent;
import com.ryannadams.cheeonk.client.event.ChatCreatedEvent;
import com.ryannadams.cheeonk.client.event.RemoveBuddyEvent;
import com.ryannadams.cheeonk.client.event.SignedinEvent;
import com.ryannadams.cheeonk.client.event.SignedoutEvent;
import com.ryannadams.cheeonk.client.handler.AuthenticationEventHandler;
import com.ryannadams.cheeonk.client.handler.BuddyListEventHandler;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.CreateChat;
import com.ryannadams.cheeonk.shared.action.GetBuddyList;
import com.ryannadams.cheeonk.shared.buddy.CheeonkBuddy;
import com.ryannadams.cheeonk.shared.chat.CheeonkChat;

public class BuddyListWidget extends Composite implements AuthenticationEventHandler, BuddyListEventHandler
{
	private final VerticalPanel panel;

	private final Button addButton;
	private final TextBox buddyName;
	private final Button okButton;

	private Timer timer;

	private final DispatchAsync dispatchAsync;

	private final SimpleEventBus eventBus;

	public BuddyListWidget(SimpleEventBus eventBus)
	{
		this.eventBus = eventBus;

		this.eventBus.addHandler(SignedinEvent.TYPE, this);
		this.eventBus.addHandler(SignedoutEvent.TYPE, this);
		this.eventBus.addHandler(AddBuddyEvent.TYPE, this);

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
						popupPanel.hide();
					}
				});
				popupPanel.add(panel);

				popupPanel.show();

			}
		});

		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.add(addButton);
		mainPanel.add(panel);

		initWidget(mainPanel);
	}

	public void setTimer(Timer timer, int periodMillis)
	{
		this.timer = timer;
		this.timer.scheduleRepeating(periodMillis);
	}

	public void cancelTimer()
	{
		timer.cancel();
	}

	public void addBuddy(CheeonkBuddy buddy, ClickHandler clickHandler)
	{
		BuddyWidget buddyWidget = new BuddyWidget(buddy);
		buddyWidget.addClickHandler(clickHandler);

		panel.add(buddyWidget);
	}

	public void removeBuddy(CheeonkBuddy buddy)
	{
		BuddyWidget buddyWidget = new BuddyWidget(buddy);

		panel.remove(buddyWidget);
	}

	public void setBuddyUnavailable(CheeonkBuddy buddy)
	{
		panel.getWidget(panel.getWidgetIndex(new BuddyWidget(buddy))).setStyleName("buddyListWidget-Available");
	}

	public void setBuddyAvailable(CheeonkBuddy buddy)
	{
		panel.getWidget(panel.getWidgetIndex(new BuddyWidget(buddy))).setStyleName("buddyListWidget-Unavailable");
	}

	public void clearBuddyList()
	{
		panel.clear();
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

	private class BuddyWidget extends Composite
	{
		private final CheeonkBuddy buddy;
		private final PushButton button;

		public BuddyWidget(CheeonkBuddy buddy)
		{
			button = new PushButton(buddy.getName());
			button.setStyleName("buddy");

			this.buddy = buddy;

			HorizontalPanel panel = new HorizontalPanel();

			panel.add(button);
			initWidget(panel);
		}

		public void addClickHandler(ClickHandler clickHandler)
		{
			button.addClickHandler(clickHandler);
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((buddy == null) ? 0 : buddy.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BuddyWidget other = (BuddyWidget) obj;

			if (buddy == null)
			{
				if (other.buddy != null)
					return false;
			}
			else
				if (!buddy.equals(other.buddy))
					return false;
			return true;
		}

	}

	@Override
	public void onSignedin(SignedinEvent event)
	{
		final ConnectionKey key = event.getConnectionKey();

		dispatchAsync.execute(new GetBuddyList(key), new GotBuddyList()
		{
			@Override
			public void got(CheeonkBuddy[] buddies)
			{
				for (final CheeonkBuddy buddy : buddies)
				{
					eventBus.fireEvent(new AddBuddyEvent(key, buddy));
				}

			}
		});

		// Set Timer to poll for buddy updates

	}

	@Override
	public void onSignedout(SignedoutEvent event)
	{
		// Cancel Timer and clear list

		clearBuddyList();
	}

	@Override
	public void onAddBuddy(AddBuddyEvent event)
	{
		// Need to add Buddy Information
		Logger.getLogger("").log(Level.INFO, "Added Buddy");

		final CheeonkBuddy buddy = event.getBuddy();
		final ConnectionKey key = event.getConnectionKey();

		addBuddy(buddy, new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{

				dispatchAsync.execute(new CreateChat(key, buddy.getJID()), new CreatedChat()
				{

					@Override
					public void got(CheeonkChat[] chats)
					{
						// Do a call to the dispatch to create the
						// chat
						// fire event with the chat object
						for (CheeonkChat chat : chats)
						{
							final ChatWidgetDialog chatWidget = new ChatWidgetDialog(eventBus);

							eventBus.fireEvent(new ChatCreatedEvent(key, chat));

							chatWidget.show();
						}
					}
				});

			}

		});

	}

	@Override
	public void onRemoveBuddy(RemoveBuddyEvent event)
	{
		// TODO Auto-generated method stub

	}
}
