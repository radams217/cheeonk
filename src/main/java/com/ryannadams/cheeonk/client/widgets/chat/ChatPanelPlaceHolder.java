package com.ryannadams.cheeonk.client.widgets.chat;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ryannadams.cheeonk.shared.buddy.JabberId;
import com.ryannadams.cheeonk.shared.message.IMessage;

public class ChatPanelPlaceHolder extends Composite implements HasClickHandlers, IChatWidget
{
	private final ChatWidgetPopup chatPopup;
	private final VerticalPanel panel;

	public ChatPanelPlaceHolder(SimpleEventBus eventBus, JabberId jabberId)
	{
		chatPopup = new ChatWidgetPopup(eventBus, jabberId);

		panel = new VerticalPanel();
		panel.add(new HTML(jabberId.toString()));

		addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				if (chatPopup.isShowing())
				{
					chatPopup.hide();
				}
				else
				{
					show();
				}
			}
		});

		initWidget(panel);
		setStyleName("chatHolder");
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler)
	{
		return addDomHandler(handler, ClickEvent.getType());
	}

	@Override
	public void show()
	{
		chatPopup.setPopupPositionAndShow(new PopupPanel.PositionCallback()
		{
			@Override
			public void setPosition(int offsetWidth, int offsetHeight)
			{
				int left = panel.getAbsoluteLeft();
				int top = panel.getAbsoluteTop() - chatPopup.getOffsetHeight();
				chatPopup.setPopupPosition(left, top);
			}
		});
	}

	@Override
	public void hide()
	{
		chatPopup.hide();
	}

	@Override
	public void addCheeonk(IMessage message)
	{
		chatPopup.addCheeonk(message);
	}

}
