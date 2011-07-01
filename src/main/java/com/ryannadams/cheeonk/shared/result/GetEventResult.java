package com.ryannadams.cheeonk.shared.result;

import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;

import com.ryannadams.cheeonk.shared.event.CheeonkEvent;

public class GetEventResult implements Result
{
	private ArrayList<CheeonkEvent> events;

	public GetEventResult()
	{
		events = new ArrayList<CheeonkEvent>();
	}

	public GetEventResult(ArrayList<CheeonkEvent> events)
	{
		this.events = events;
	}

	public ArrayList<CheeonkEvent> getEvents()
	{
		return new ArrayList<CheeonkEvent>(events);
	}

}
