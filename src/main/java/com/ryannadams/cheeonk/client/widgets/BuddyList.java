package com.ryannadams.cheeonk.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;

public class BuddyList extends Composite
{
	// Probably will want to change this later
	private FlowPanel flowPanel;
	private FlexTable flexTable;

	public BuddyList()
	{
		flexTable = new FlexTable();
		flowPanel = new FlowPanel();

		flexTable.clear();
		flexTable.setWidget(3, 0, flowPanel);

		flowPanel.addStyleName("buddyList");

		initWidget(flowPanel);
	}

	public void addBuddy(BuddyWidget buddy)
	{
		flowPanel.add(buddy);
	}

	public void clearBuddyList()
	{
		flowPanel.clear();
	}

	// public void setBuddyList(ClientBuddy[] buddyList)
	// {
	// for (ClientBuddy buddy : buddyList)
	// {
	// Button button = new Button(buddy.getName());
	// button.setStyleName("buddyList-Status");
	//
	// button.addClickHandler(this);
	//
	// flowPanel.add(button);
	// }
	// }
}
