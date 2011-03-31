package com.ryannadams.cheeonk.client.widgets;

import java.util.List;

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

		for (String buddy : buddyList)
		{
			Button label = new Button(buddy);
			flowpanel.add(label);
		}

		initWidget(flowpanel);

	}

}
