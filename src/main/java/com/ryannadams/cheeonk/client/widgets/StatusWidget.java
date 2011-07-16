package com.ryannadams.cheeonk.client.widgets;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.ImageResources;
import com.ryannadams.cheeonk.client.callback.ChangedPresence;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.ChangePresence;
import com.ryannadams.cheeonk.shared.buddy.CheeonkPresence;
import com.ryannadams.cheeonk.shared.buddy.CheeonkPresence.Mode;
import com.ryannadams.cheeonk.shared.buddy.CheeonkPresence.Type;

public class StatusWidget extends Composite
{
	private final VerticalPanel panel;

	private Label name;
	private TextBox status;
	private Image statusDot;

	private final DispatchAsync dispatchAsync;

	public StatusWidget(SimpleEventBus eventBus)
	{
		panel = new VerticalPanel();
		statusDot = new Image(ImageResources.INSTANCE.getGreenDot());
		dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());

		VerticalPanel buddyLocal = new VerticalPanel();

		HorizontalPanel statusDotNamePanel = new HorizontalPanel();
		statusDotNamePanel.add(statusDot);
		this.statusDot.setStyleName("buddyWidget-statusDot");

		statusDotNamePanel.add(new HTML(ConnectionKey.get().getUsername()));

		buddyLocal.add(statusDotNamePanel);

		final TextBox statusBox = new TextBox();

		statusBox.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				statusBox.setReadOnly(false);

			}
		});

		statusBox.addKeyDownHandler(new KeyDownHandler()
		{
			@Override
			public void onKeyDown(KeyDownEvent event)
			{
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
				{
					dispatchAsync.execute(new ChangePresence(ConnectionKey.get(), new CheeonkPresence(Type.AVAILABLE, statusBox.getText(), Mode.AVAILABLE)),
							new ChangedPresence()
							{
								@Override
								public void got()
								{
									statusBox.setReadOnly(true);
								}
							});

				}

			}
		});

		buddyLocal.add(statusBox);

		panel.add(buddyLocal);

		initWidget(panel);

	}
}
