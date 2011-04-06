package com.ryannadams.cheeonk.client.widgets;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.ryannadams.cheeonk.client.chat.IBuddy;

public class BuddyList extends Composite implements ClickHandler
{
	// Probably will want to change this later
	private FlowPanel flowpanel;
	private FlexTable flextable;
	private List<IBuddy> buddyList;

	public BuddyList()
	{
		flextable = new FlexTable();
		flowpanel = new FlowPanel();

		flextable.clear();
		flextable.setWidget(3, 0, flowpanel);

		flowpanel.addStyleName("buddyList");

		initWidget(flowpanel);
	}

	public void setBuddyList(IBuddy[] buddyList)
	{
		for (IBuddy buddy : buddyList)
		{
			Button button = new Button(buddy.getName());
			button.setStyleName("buddyList-Status");

			button.addClickHandler(this);

			flowpanel.add(button);

			this.buddyList.add(buddy);
		}
	}

	// public BuddyList(IBuddy[] buddyList)
	// {
	// flextable = new FlexTable();
	// flowpanel = new FlowPanel();
	//
	// flextable.clear();
	// flextable.setWidget(3, 0, flowpanel);
	//
	// flowpanel.addStyleName("buddyList");
	//
	// for (IBuddy buddy : buddyList)
	// {
	// Button button = new Button(buddy.getName());
	// button.setStyleName("buddyList-Status");
	//
	// button.addClickHandler(this);
	//
	// flowpanel.add(button);
	//
	// this.buddyList.add(buddy);
	// }
	//
	// initWidget(flowpanel);
	//
	// }

	@Override
	public void onClick(ClickEvent event)
	{

	}
}
