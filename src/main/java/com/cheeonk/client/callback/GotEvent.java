package com.cheeonk.client.callback;

import com.cheeonk.shared.result.GetEventResult;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class GotEvent implements AsyncCallback<GetEventResult>
{
	@Override
	public void onFailure(Throwable caught)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(GetEventResult result)
	{
		got(result.getEvents().toArray(new GwtEvent[result.getEvents().size()]));
	}

	public abstract void got(GwtEvent[] events);
}
