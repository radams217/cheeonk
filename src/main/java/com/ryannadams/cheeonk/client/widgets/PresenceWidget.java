package com.ryannadams.cheeonk.client.widgets;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.ImageResources;
import com.ryannadams.cheeonk.client.callback.ChangedPresence;
import com.ryannadams.cheeonk.client.event.RemoveBuddyEvent;
import com.ryannadams.cheeonk.client.handler.BuddyEventHandler;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.ChangePresence;
import com.ryannadams.cheeonk.shared.buddy.CheeonkPresence;
import com.ryannadams.cheeonk.shared.buddy.CheeonkPresence.Mode;
import com.ryannadams.cheeonk.shared.buddy.JabberId;
import com.ryannadams.cheeonk.shared.event.AddBuddyEvent;
import com.ryannadams.cheeonk.shared.event.PresenceChangeEvent;

public class PresenceWidget extends Composite implements BuddyEventHandler
{
	private final VerticalPanel panel;

	private Label name;
	private TextBox status;
	private Image statusDot;
	private StatusPopupPanel popup;

	private final DispatchAsync dispatchAsync;

	protected final SimpleEventBus eventBus;

	public PresenceWidget(final SimpleEventBus eventBus)
	{
		this.eventBus = eventBus;
		this.eventBus.addHandler(PresenceChangeEvent.TYPE, this);
		panel = new VerticalPanel();
		statusDot = new Image(ImageResources.INSTANCE.getGreenDot());
		dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

		VerticalPanel buddyLocal = new VerticalPanel();

		HorizontalPanel statusDotNamePanel = new HorizontalPanel();
		statusDotNamePanel.add(statusDot);
		this.statusDot.setStyleName("buddyWidget-statusDot");

		statusDotNamePanel.add(new HTML(ConnectionKey.get().getUsername()));

		buddyLocal.add(statusDotNamePanel);

		this.status = new TextBox();

		status.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				status.setReadOnly(false);

				popup.setPopupPositionAndShow(new PopupPanel.PositionCallback()
				{
					@Override
					public void setPosition(int offsetWidth, int offsetHeight)
					{
						int left = status.getAbsoluteLeft();
						int top = status.getAbsoluteTop() + status.getOffsetHeight();
						popup.setPopupPosition(left, top);
					}
				});

			}
		});

		status.addKeyDownHandler(new KeyDownHandler()
		{
			@Override
			public void onKeyDown(KeyDownEvent event)
			{
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				{
					dispatchAsync.execute(
							new ChangePresence(ConnectionKey.get(), new CheeonkPresence(ConnectionKey.get().getJabberId(), Mode.AVAILABLE, status.getText())),
							new ChangedPresence()
							{
								@Override
								public void got(CheeonkPresence cheeonkPresence)
								{
									popup.hide();
									status.setReadOnly(true);
									eventBus.fireEvent(new PresenceChangeEvent(cheeonkPresence));
								}
							});

				}

			}
		});

		popup = new StatusPopupPanel();

		buddyLocal.add(status);

		panel.add(buddyLocal);

		initWidget(panel);

	}

	private class StatusPopupPanel extends PopupPanel
	{
		public StatusPopupPanel()
		{
			super(true);

			VerticalPanel panel = new VerticalPanel();
			panel.add(new StatusEntry(new Image(ImageResources.INSTANCE.getGreenDot()), "Available", new CheeonkPresence(new JabberId(""), Mode.AVAILABLE,
					status.getText())));
			panel.add(new StatusEntry(new Image(ImageResources.INSTANCE.getYellowDot()), "Away", new CheeonkPresence(new JabberId(""), Mode.AWAY, status
					.getText())));
			panel.add(new StatusEntry(new Image(ImageResources.INSTANCE.getRedDot()), "DND", new CheeonkPresence(new JabberId(""), Mode.DND, status.getText())));

			add(panel);
		}

		private class StatusEntry extends Composite implements HasClickHandlers
		{
			public StatusEntry(Image image, String statusDescription, final CheeonkPresence cheeonkPresence)
			{
				HorizontalPanel panel = new HorizontalPanel();
				panel.setStyleName("statusEntry");
				panel.add(image);
				image.setStyleName("buddyWidget-statusDot");
				panel.add(new HTML(statusDescription));

				addClickHandler(new ClickHandler()
				{
					@Override
					public void onClick(ClickEvent event)
					{
						dispatchAsync.execute(new ChangePresence(ConnectionKey.get(), cheeonkPresence), new ChangedPresence()
						{
							@Override
							public void got(CheeonkPresence cheeonkPresence)
							{
								popup.hide();
								eventBus.fireEvent(new PresenceChangeEvent(cheeonkPresence));
							}
						});
					}

				});

				initWidget(panel);
			}

			@Override
			public HandlerRegistration addClickHandler(ClickHandler handler)
			{
				return addDomHandler(handler, ClickEvent.getType());
			}

		}

	}

	@Override
	public void onAddBuddy(AddBuddyEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onRemoveBuddy(RemoveBuddyEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onPresenceChange(PresenceChangeEvent event)
	{
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
	}

}
