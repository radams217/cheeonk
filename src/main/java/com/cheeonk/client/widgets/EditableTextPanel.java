package com.cheeonk.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;

public class EditableTextPanel extends Composite implements KeyDownHandler, DoubleClickHandler, ClickHandler
{
	private final HorizontalPanel panel;
	private final TextBox textBox;
	private final HTML html;
	private final PushButton editButton;

	private String text;

	public EditableTextPanel(String text)
	{
		this.panel = new HorizontalPanel();

		this.text = text;

		this.textBox = new TextBox();
		this.textBox.addKeyDownHandler(this);
		this.html = new HTML(this.text);
		this.html.addDoubleClickHandler(this);

		this.editButton = new PushButton("Edit", this);

		this.panel.add(html);

		if (!hasText())
		{
			this.panel.add(editButton);
		}

		initWidget(panel);
	}

	public String getText()
	{
		return text;
	}

	public boolean hasText()
	{
		return text != null && text != "";
	}

	@Override
	public void onDoubleClick(DoubleClickEvent event)
	{
		panel.clear();
		panel.add(textBox);
	}

	@Override
	public void onKeyDown(KeyDownEvent event)
	{
		if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode())
		{
			text = textBox.getText();
			html.setText(text);
			panel.clear();
			panel.add(html);
		}
	}

	@Override
	public void onClick(ClickEvent event)
	{
		panel.clear();
		panel.add(textBox);
	}
}
