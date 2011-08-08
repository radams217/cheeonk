package com.cheeonk.client.widgets;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.cheeonk.client.ImageResources;
import com.cheeonk.client.callback.ChangedPresence;
import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.action.ChangePresence;
import com.cheeonk.shared.buddy.CheeonkPresence;
import com.cheeonk.shared.buddy.CheeonkPresence.Mode;
import com.cheeonk.shared.buddy.JabberId;
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
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PresenceWidget extends Composite
{
	private final DispatchAsync dispatchAsync;
	private final VerticalPanel panel;

	private Image statusDot;
	private TextBox status;
	private CheeonkPresence presence;
	private StatusPopupPanel popup;

	public PresenceWidget(final SimpleEventBus eventBus, final JabberId jabberId)
	{
		this.dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

		this.panel = new VerticalPanel();
		this.statusDot = new Image(ImageResources.INSTANCE.getGreenDot());
		this.statusDot.setStyleName("buddyWidget-statusDot");
		this.status = new TextBox();
		this.status.setStyleName("presenceWidget-status");
		this.presence = new CheeonkPresence(jabberId, Mode.AVAILABLE, status.getText());

		this.statusDot.addClickHandler(new ClickHandler()
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
						int left = statusDot.getAbsoluteLeft();
						int top = statusDot.getAbsoluteTop() + statusDot.getOffsetHeight();
						popup.setPopupPosition(left, top);
					}
				});
			}
		});

		this.status.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				status.setReadOnly(false);
			}
		});

		this.status.addKeyDownHandler(new KeyDownHandler()
		{
			@Override
			public void onKeyDown(KeyDownEvent event)
			{
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				{
					presence.setStatus(status.getText());

					dispatchAsync.execute(new ChangePresence(ConnectionKey.get(), presence), new ChangedPresence()
					{
						@Override
						public void got(CheeonkPresence cheeonkPresence)
						{
							popup.hide();
							status.setReadOnly(true);
							setPresence(cheeonkPresence);
						}
					});

				}

			}
		});
		this.popup = new StatusPopupPanel();

		HorizontalPanel statusDotNamePanel = new HorizontalPanel();
		statusDotNamePanel.add(statusDot);
		statusDotNamePanel.add(new HTML(jabberId.toString()));

		panel.add(statusDotNamePanel);
		panel.add(status);

		initWidget(panel);
		addStyleName("presenceWidget");
	}

	private class StatusPopupPanel extends PopupPanel
	{
		public StatusPopupPanel()
		{
			super(true);

			VerticalPanel panel = new VerticalPanel();
			panel.add(new StatusEntry(new Image(ImageResources.INSTANCE.getGreenDot()), "Available", new CheeonkPresence(presence.getJabberId(),
					Mode.AVAILABLE, status.getText())));
			panel.add(new StatusEntry(new Image(ImageResources.INSTANCE.getYellowDot()), "Away", new CheeonkPresence(presence.getJabberId(), Mode.AWAY, status
					.getText())));
			panel.add(new StatusEntry(new Image(ImageResources.INSTANCE.getRedDot()), "DND", new CheeonkPresence(presence.getJabberId(), Mode.DND, status
					.getText())));

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
						cheeonkPresence.setStatus(status.getText());
						dispatchAsync.execute(new ChangePresence(ConnectionKey.get(), cheeonkPresence), new ChangedPresence()
						{
							@Override
							public void got(CheeonkPresence cheeonkPresence)
							{
								popup.hide();
								setPresence(cheeonkPresence);
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

	private void setPresence(CheeonkPresence presence)
	{
		this.presence = presence;

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
