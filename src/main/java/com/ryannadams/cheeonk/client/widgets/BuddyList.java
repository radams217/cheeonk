package com.ryannadams.cheeonk.client.widgets;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;

public class BuddyList extends Composite
{
	// Probably will want to change this later
	private FlowPanel flowpanel;
	private FlexTable flextable;

	public BuddyList(List<String> buddyList)
	{
		flextable = new FlexTable();
		flowpanel = new FlowPanel();

		flextable.clear();
		flextable.setWidget(3, 0, flowpanel);

		flowpanel.addStyleName("buddyList");

		for (final String buddy : buddyList)
		{
			Button button = new Button(buddy);

			final ChatPanel chat = new ChatPanel(true);

			button.addClickHandler(new ClickHandler()
			{

				@Override
				public void onClick(ClickEvent event)
				{

					chat.setUsername(buddy);
					chat.show();

					chat.addClickHandler(new ClickHandler()
					{

						@Override
						public void onClick(ClickEvent event)
						{
							// TODO Auto-generated method stub

						}
					});

				}
			});

			flowpanel.add(button);
		}

		initWidget(flowpanel);

	}
}
