package com.ryannadams.cheeonk.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.client.chat.ClientBuddy;

public class BuddyList extends Composite
{
	// Probably will want to change this later
	private FlowPanel flowPanel;
	private FlexTable flexTable;
	private final Button addButton;
	private final TextBox buddyName;
	private final Button okButton;

	public BuddyList()
	{
		VerticalPanel panel = new VerticalPanel();

		flexTable = new FlexTable();
		flowPanel = new FlowPanel();

		buddyName = new TextBox();
		okButton = new Button("ok");

		addButton = new Button("Add Buddy");
		addButton.addClickHandler(new ClickHandler()
		{

			@Override
			public void onClick(ClickEvent event)
			{
				final PopupPanel popupPanel = new PopupPanel(true);

				popupPanel.setPopupPositionAndShow(new PopupPanel.PositionCallback()
				{
					@Override
					public void setPosition(int offsetWidth, int offsetHeight)
					{
						// TODO: Take out the hardcoded 200
						int left = getAbsoluteLeft();
						int top = addButton.getAbsoluteTop() + addButton.getOffsetHeight();
						popupPanel.setPopupPosition(left, top);
					}
				});

				HorizontalPanel panel = new HorizontalPanel();
				panel.add(buddyName);
				panel.add(okButton);

				okButton.addClickHandler(new ClickHandler()
				{

					@Override
					public void onClick(ClickEvent event)
					{
						addBuddy(new BuddyWidget(new ClientBuddy(buddyName.getText(), buddyName.getText())));
						popupPanel.hide();

					}
				});
				popupPanel.add(panel);

				popupPanel.show();

			}
		});

		flexTable.clear();
		flexTable.setWidget(3, 0, flowPanel);

		flowPanel.addStyleName("buddyList");

		panel.add(flowPanel);
		panel.add(addButton);

		initWidget(panel);
	}

	public void addBuddy(BuddyWidget buddy)
	{
		flowPanel.add(buddy);
	}

	public void clearBuddyList()
	{
		flowPanel.clear();
	}
}
