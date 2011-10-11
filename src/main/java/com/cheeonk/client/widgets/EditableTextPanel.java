package com.cheeonk.client.widgets;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;

public class EditableTextPanel extends Composite implements KeyDownHandler, DoubleClickHandler
{
	private final HorizontalPanel panel;
	private final TextBox textBox;
	private final HTML html;

	public EditableTextPanel(String text)
	{
		this.panel = new HorizontalPanel();

		this.textBox = new TextBox();
		this.textBox.addKeyDownHandler(this);
		this.html = new HTML(text);
		this.html.addDoubleClickHandler(this);

		this.panel.add(html);

		initWidget(panel);
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
			html.setText(textBox.getText());
			panel.clear();
			panel.add(html);
		}
	}

	public String getText()
	{
		return textBox.getText();
	}
}
