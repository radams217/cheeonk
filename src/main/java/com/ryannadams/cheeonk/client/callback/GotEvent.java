package com.ryannadams.cheeonk.client.callback;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ryannadams.cheeonk.shared.event.CheeonkEvent;
import com.ryannadams.cheeonk.shared.result.GetEventResult;

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
		got(result.getEvents().toArray(new CheeonkEvent[result.getEvents().size()]));
	}

	public abstract void got(CheeonkEvent[] events);
}
