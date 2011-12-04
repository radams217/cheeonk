package com.cheeonk.client.widgets;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.cheeonk.client.ImageResources;
import com.cheeonk.client.callback.UpdatedBuddy;
import com.cheeonk.client.event.ChatCreatedEvent;
import com.cheeonk.client.handler.BuddyEventHandler;
import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.action.UpdateBuddy;
import com.cheeonk.shared.buddy.CheeonkPresence;
import com.cheeonk.shared.buddy.IBuddy;
import com.cheeonk.shared.event.AddBuddyEvent;
import com.cheeonk.shared.event.PresenceChangeEvent;
import com.cheeonk.shared.event.RemoveBuddyEvent;
import com.cheeonk.shared.event.UpdateBuddyEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
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

public abstract class BuddyWidget extends Composite implements HasMouseOverHandlers, MouseOverHandler, HasMouseOutHandlers, MouseOutHandler, HasClickHandlers,
		ClickHandler, BuddyEventHandler
{
	private final SimpleEventBus eventBus;
	private final DispatchAsync dispatchAsync;

	private final VerticalPanel panel;
	private final BuddyPopupPanel detailsPopupPanel;

	private HTML buddyStatus;
	private Image statusDot;

	private final EditableTextPanel editBuddyName;

	public BuddyWidget(SimpleEventBus eventBus)
	{
		this.eventBus = eventBus;
		this.eventBus.addHandler(PresenceChangeEvent.TYPE, this);

		this.dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

		this.statusDot = new Image(ImageResources.INSTANCE.getGrayDot());
		this.statusDot.setStyleName("buddyWidget-statusDot");
		this.editBuddyName = new EditableTextPanel(getBuddy().getName())
		{
			@Override
			public void onKeyDown(KeyDownEvent event)
			{
				super.onKeyDown(event);

				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				{
					getBuddy().setName(this.getText());

					dispatchAsync.execute(new UpdateBuddy(ConnectionKey.get(), getBuddy()), new UpdatedBuddy()
					{
						@Override
						public void got()
						{

						}

					});

				}
			}

		};
		this.editBuddyName.setStyleName("buddyWidget-buddy");

		this.detailsPopupPanel = new BuddyPopupPanel();
		this.detailsPopupPanel.addChatClickHandler(this);

		this.addMouseOutHandler(this);
		this.addMouseOverHandler(this);
		this.addClickHandler(this);

		buddyStatus = new HTML("");
		buddyStatus.setStyleName("buddyWidget-status");

		panel = new VerticalPanel();
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		panel.setStyleName("buddyWidget");
		HorizontalPanel statusDotNamePanel = new HorizontalPanel();
		statusDotNamePanel.add(statusDot);
		statusDotNamePanel.add(editBuddyName);

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
		eventBus.fireEvent(new ChatCreatedEvent(getBuddy()));
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

			HTML buddyName = new HTML(getBuddy().getName());
			buddyName.setStyleName("buddyPopupPanel-name");

			HTML jabberId = new HTML(getBuddy().getJabberId().getJabberId());
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
		if (!getBuddy().getJabberId().equals(event.getPresence().getJabberId()))
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

	@Override
	public void onUpdateBuddy(UpdateBuddyEvent event)
	{
		// TODO Auto-generated method stub

	}

	public abstract IBuddy getBuddy();
}
