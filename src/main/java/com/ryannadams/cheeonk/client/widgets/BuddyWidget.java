package com.ryannadams.cheeonk.client.widgets;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.callback.GotBuddyPresence;
import com.ryannadams.cheeonk.client.event.SignedinEvent;
import com.ryannadams.cheeonk.client.event.SignedoutEvent;
import com.ryannadams.cheeonk.client.handler.AuthenticationEventHandler;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.GetBuddyPresence;
import com.ryannadams.cheeonk.shared.buddy.CheeonkBuddy;

public class BuddyWidget extends Composite implements AuthenticationEventHandler, MouseOverHandler
{
	private final CheeonkBuddy buddy;
	private final PushButton button;

	private final DispatchAsync dispatchAsync;

	private final Timer timer;

	private final SimpleEventBus eventBus;

	public BuddyWidget(final SimpleEventBus eventBus, final CheeonkBuddy buddy)
	{
		this.eventBus = eventBus;
		this.buddy = buddy;

		this.eventBus.addHandler(SignedoutEvent.TYPE, this);

		this.button = new PushButton(buddy.getName());
		this.button.setStyleName("buddy");

		this.button.addMouseOverHandler(this);

		this.dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

		this.timer = new Timer()
		{
			@Override
			public void run()
			{
				dispatchAsync.execute(new GetBuddyPresence(ConnectionKey.get(), buddy.getJID()), new GotBuddyPresence()
				{
					@Override
					public void got(boolean isAvailable)
					{
						if (isAvailable)
						{
							setAvailable();
						}
						else
						{
							setUnavailable();
						}
					}
				});

			}
		};

		this.timer.scheduleRepeating(8000);

		VerticalPanel panel = new VerticalPanel();
		panel.add(button);
		panel.add(new HTML("[Status]"));
		initWidget(panel);
	}

	private void setUnavailable()
	{
		setStyleName("buddyWidget-Unavailable");
	}

	private void setAvailable()
	{
		setStyleName("buddyWidget-Available");
	}

	public void addClickHandler(ClickHandler clickHandler)
	{
		button.addClickHandler(clickHandler);
	}

	@Deprecated
	@Override
	public void onSignedin(SignedinEvent event)
	{

	}

	@Override
	public void onSignedout(SignedoutEvent event)
	{
		timer.cancel();
	}

	@Override
	public void onMouseOver(MouseOverEvent event)
	{
		final BuddyPopupPanel buddyPopupPanel = new BuddyPopupPanel();

		buddyPopupPanel.setPopupPositionAndShow(new PopupPanel.PositionCallback()
		{
			@Override
			public void setPosition(int offsetWidth, int offsetHeight)
			{
				int left = (getAbsoluteLeft() + getOffsetWidth());
				int top = getAbsoluteTop();
				buddyPopupPanel.setPopupPosition(left, top);
			}
		});

		buddyPopupPanel.show();

	}

	private class BuddyPopupPanel extends PopupPanel
	{
		public BuddyPopupPanel()
		{
			super(true);

			VerticalPanel panel = new VerticalPanel();
			panel.setStyleName("signinPanel");
			panel.add(new HTML(buddy.getName()));

			add(panel);
		}

	}

}
