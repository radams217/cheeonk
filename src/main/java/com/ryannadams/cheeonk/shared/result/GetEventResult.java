package com.ryannadams.cheeonk.shared.result;

import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.event.shared.GwtEvent;

public class GetEventResult implements Result
{
	private ArrayList<GwtEvent> events;

	public GetEventResult()
	{
		events = new ArrayList<GwtEvent>();
	}

	public GetEventResult(ArrayList<GwtEvent> events)
	{
		this.events = events;
	}

	public ArrayList<GwtEvent> getEvents()
	{
		return new ArrayList<GwtEvent>(events);
	}

}
