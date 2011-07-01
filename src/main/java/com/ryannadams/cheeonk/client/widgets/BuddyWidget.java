package com.ryannadams.cheeonk.client.widgets;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.shared.buddy.IBuddy;

public class BuddyWidget extends Composite implements MouseOverHandler
{
	private final IBuddy buddy;
	private final PushButton button;

	private final DispatchAsync dispatchAsync;

	private final SimpleEventBus eventBus;

	public BuddyWidget(final SimpleEventBus eventBus, final IBuddy buddy)
	{
		this.eventBus = eventBus;
		this.buddy = buddy;

		this.button = new PushButton(buddy.getName());
		this.button.setStyleName("buddy");

		this.button.addMouseOverHandler(this);

		this.dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

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
			panel.add(new HTML(buddy.getJID().getJabberId()));

			add(panel);
		}

	}

}
