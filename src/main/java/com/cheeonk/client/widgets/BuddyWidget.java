package com.cheeonk.client.widgets;

import com.cheeonk.client.ImageResources;
import com.cheeonk.client.event.ChatCreatedEvent;
import com.cheeonk.client.event.RemoveBuddyEvent;
import com.cheeonk.client.handler.BuddyEventHandler;
import com.cheeonk.shared.buddy.CheeonkPresence;
import com.cheeonk.shared.buddy.IBuddy;
import com.cheeonk.shared.event.AddBuddyEvent;
import com.cheeonk.shared.event.PresenceChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BuddyWidget extends Composite implements HasMouseOverHandlers, MouseOverHandler, HasMouseOutHandlers, MouseOutHandler, HasClickHandlers,
		ClickHandler, BuddyEventHandler
{
	protected final IBuddy buddy;
	private final SimpleEventBus eventBus;
	private final BuddyPopupPanel detailsPopupPanel;

	private HTML buddyStatus;

	private Image statusDot;

	public BuddyWidget(SimpleEventBus eventBus, IBuddy buddy)
	{
		this.eventBus = eventBus;
		this.eventBus.addHandler(PresenceChangeEvent.TYPE, this);

		this.buddy = buddy;
		this.statusDot = new Image(ImageResources.INSTANCE.getGrayDot());
		this.statusDot.setStyleName("buddyWidget-statusDot");

		this.detailsPopupPanel = new BuddyPopupPanel();
		this.detailsPopupPanel.addChatClickHandler(this);

		this.addMouseOutHandler(this);
		this.addMouseOverHandler(this);
		this.addClickHandler(this);

		HTML buddyName = new HTML(this.buddy.getName());
		buddyName.setStyleName("buddyWidget-buddy");

		buddyStatus = new HTML("");
		buddyStatus.setStyleName("buddyWidget-status");

		VerticalPanel panel = new VerticalPanel();
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		panel.setStyleName("buddyWidget");
		HorizontalPanel statusDotNamePanel = new HorizontalPanel();
		statusDotNamePanel.add(statusDot);
		statusDotNamePanel.add(buddyName);

		panel.add(statusDotNamePanel);
		panel.add(buddyStatus);

		initWidget(panel);
	}

	@Override
	public void onMouseOver(MouseOverEvent event)
	{
		this.detailsPopupPanel.setPopupPositionAndShow(new PopupPanel.PositionCallback()
		{
			@Override
			public void setPosition(int offsetWidth, int offsetHeight)
			{
				int left = getAbsoluteLeft() + getOffsetWidth() - 30;
				int top = getAbsoluteTop();
				detailsPopupPanel.setPopupPosition(left, top);
			}
		});
	}

	@Override
	public void onMouseOut(MouseOutEvent event)
	{
		this.detailsPopupPanel.hide();
	}

	@Override
	public void onClick(ClickEvent event)
	{
		eventBus.fireEvent(new ChatCreatedEvent(buddy));
	}

	private class BuddyPopupPanel extends DecoratedPopupPanel implements HasMouseOutHandlers, MouseOutHandler, HasMouseOverHandlers, MouseOverHandler
	{
		private final PushButton chatButton;
		private Image statusDot;

		public BuddyPopupPanel()
		{
			super(false);

			this.chatButton = new PushButton("Chat");
			this.statusDot = new Image(ImageResources.INSTANCE.getGrayDot());
			this.statusDot.setStyleName("buddyWidget-statusDot");

			this.addMouseOutHandler(this);
			this.addMouseOverHandler(this);

			HTML buddyName = new HTML(buddy.getName());
			buddyName.setStyleName("buddyPopupPanel-name");

			HTML jabberId = new HTML(buddy.getJabberId().getJabberId());
			jabberId.setStyleName("buddyPopupPanel-jabberId");

			VerticalPanel panel = new VerticalPanel();
			panel.setStyleName("buddyPopupPanel");

			HorizontalPanel statusDotNamePanel = new HorizontalPanel();
			statusDotNamePanel.add(this.statusDot);
			statusDotNamePanel.add(buddyName);

			panel.add(statusDotNamePanel);
			panel.add(jabberId);
			panel.add(chatButton);

			add(panel);
		}

		public void addChatClickHandler(ClickHandler clickHandler)
		{
			chatButton.addClickHandler(clickHandler);
		}

		@Override
		public void onMouseOut(MouseOutEvent event)
		{
			hide();
		}

		@Override
		public void onMouseOver(MouseOverEvent event)
		{

		}

		@Override
		public HandlerRegistration addMouseOutHandler(MouseOutHandler handler)
		{
			return addDomHandler(handler, MouseOutEvent.getType());
		}

		@Override
		public HandlerRegistration addMouseOverHandler(MouseOverHandler handler)
		{
			return addDomHandler(handler, MouseOverEvent.getType());
		}
	}

	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler)
	{
		return addDomHandler(handler, MouseOverEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler)
	{
		return addDomHandler(handler, MouseOutEvent.getType());
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler)
	{
		return addDomHandler(handler, ClickEvent.getType());
	}

	@Override
	@Deprecated
	public void onAddBuddy(AddBuddyEvent event)
	{

	}

	@Override
	@Deprecated
	public void onRemoveBuddy(RemoveBuddyEvent event)
	{

	}

	@Override
	public void onPresenceChange(PresenceChangeEvent event)
	{
		if (!buddy.getJabberId().equals(event.getPresence().getJabberId()))
		{
			return;
		}

		CheeonkPresence presence = event.getPresence();
		ImageResource image = ImageResources.INSTANCE.getGrayDot();

		if (presence.isAvailable())
		{
			switch (presence.getMode())
			{
				case AVAILABLE:
				case CHAT:
					image = ImageResources.INSTANCE.getGreenDot();
					break;

				case AWAY:
					image = ImageResources.INSTANCE.getYellowDot();
					break;

				case DND:
				case XA:
					image = ImageResources.INSTANCE.getRedDot();
					break;
			}
		}

		statusDot.setResource(image);
		buddyStatus.setHTML(presence.getStatus());
	}
}