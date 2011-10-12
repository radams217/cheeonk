package com.cheeonk.client.widgets;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.cheeonk.client.callback.ChangedPresence;
import com.cheeonk.client.callback.UpdatedBuddy;
import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.action.ChangePresence;
import com.cheeonk.shared.action.UpdateBuddy;
import com.cheeonk.shared.buddy.CheeonkPresence;
import com.cheeonk.shared.buddy.IBuddy;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SubscribeBuddyWidget extends Composite
{
	private final DispatchAsync dispatchAsync;
	private final VerticalPanel panel;

	private final TextBox buddyName;
	private final PushButton accept;
	private final PushButton deny;

	public SubscribeBuddyWidget(final IBuddy buddy)
	{
		this.panel = new VerticalPanel();

		this.dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());
		this.buddyName = new TextBox();
		this.accept = new PushButton("accept", new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				CheeonkPresence presence = new CheeonkPresence(buddy.getJabberId());
				presence.setType(CheeonkPresence.Type.SUBSCRIBED);

				dispatchAsync.execute(new ChangePresence(ConnectionKey.get(), presence), new ChangedPresence()
				{
					@Override
					public void got(CheeonkPresence cheeonkPresence)
					{
						panel.clear();
					}
				});

				buddy.setName(buddyName.getText());
				dispatchAsync.execute(new UpdateBuddy(ConnectionKey.get(), buddy), new UpdatedBuddy()
				{
					@Override
					public void got()
					{
						// TODO Auto-generated method stub

					}
				});

			}
		});

		this.deny = new PushButton("deny", new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				CheeonkPresence presence = new CheeonkPresence(buddy.getJabberId());
				presence.setType(CheeonkPresence.Type.UNSUBSCRIBED);

				dispatchAsync.execute(new ChangePresence(ConnectionKey.get(), presence), new ChangedPresence()
				{
					@Override
					public void got(CheeonkPresence cheeonkPresence)
					{
						panel.clear();
					}
				});

			}
		});

		panel.add(new HTML(buddy.getJabberId().getJabberId() + " would like to add you as a buddy."));
		HorizontalPanel buddyNamePanel = new HorizontalPanel();
		buddyNamePanel.add(new HTML("Enter Buddy Name:"));
		buddyNamePanel.add(buddyName);
		panel.add(buddyNamePanel);
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(accept);
		buttonPanel.add(deny);
		panel.add(buttonPanel);

		initWidget(panel);
	}

}
