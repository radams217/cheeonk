package com.cheeonk.client.widgets.registration;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RegistrationLinkWidget extends Composite implements ClickHandler
{
	private final VerticalPanel panel;
	private final Button registerButton;

	public RegistrationLinkWidget()
	{
		panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		registerButton = new Button("Create an Account");
		registerButton.addStyleName("registrationLinkWidget-button");
		registerButton.addClickHandler(this);

		HTML registerNow = new HTML("New to Cheeonk? Register Now.");
		registerNow.addStyleName("registrationLinkWidget-registerNow");
		panel.add(registerNow);
		panel.add(registerButton);

		initWidget(panel);
		setStyleName("registrationLinkWidget");
	}

	@Override
	public void onClick(ClickEvent event)
	{

	}
}
