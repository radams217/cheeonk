package com.ryannadams.cheeonk.client.widgets;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.ryannadams.cheeonk.client.ImageResources;

public class DatePickerTextBox extends Composite
{
	private TextBox birthDateField;
	private PushButton calendarButton;
	private DatePicker birthDatePicker;

	private Date selectedDate;

	public DatePickerTextBox()
	{
		birthDateField = new TextBox();
		birthDateField.setStyleName("Date-Picker-TextBox");
		birthDateField.setReadOnly(true);
		birthDateField.setEnabled(false);

		birthDatePicker = new DatePicker();
		birthDatePicker.addValueChangeHandler(new ValueChangeHandler<Date>()
		{
			@Override
			public void onValueChange(ValueChangeEvent<Date> event)
			{
				selectedDate = event.getValue();
				birthDateField.setText(selectedDate.toString());
			}
		});

		calendarButton = new PushButton(new Image(ImageResources.INSTANCE.getCalendar()));
		calendarButton.setStyleName("Date-Picker-Button");
		calendarButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				final PopupPanel popup = new PopupPanel(true);
				popup.setStyleName("Date-Picker-Popup");

				popup.setPopupPositionAndShow(new PopupPanel.PositionCallback()
				{
					@Override
					public void setPosition(int offsetWidth, int offsetHeight)
					{
						int left = calendarButton.getAbsoluteLeft();
						int top = calendarButton.getAbsoluteTop() + calendarButton.getOffsetHeight();
						popup.setPopupPosition(left, top);
					}
				});

				birthDatePicker.addValueChangeHandler(new ValueChangeHandler<Date>()
				{

					@Override
					public void onValueChange(ValueChangeEvent<Date> event)
					{
						birthDateField.setText(event.getValue().toString());
						popup.hide();
					}
				});

				popup.add(birthDatePicker);
				popup.show();

			}
		});

		HorizontalPanel panel = new HorizontalPanel();
		panel.add(birthDateField);
		panel.add(calendarButton);

		initWidget(panel);
	}

	public String getText()
	{
		return birthDateField.getText();
	}

	public Date getSelectedDate()
	{
		return selectedDate;
	}

}
