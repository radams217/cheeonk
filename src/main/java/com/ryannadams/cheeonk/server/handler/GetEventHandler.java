package com.ryannadams.cheeonk.server.handler;

import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.gwt.event.shared.GwtEvent;
import com.ryannadams.cheeonk.server.Connection;
import com.ryannadams.cheeonk.server.ConnectionDriver;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.GetEvent;
import com.ryannadams.cheeonk.shared.result.GetEventResult;

public class GetEventHandler implements ActionHandler<GetEvent, GetEventResult>
{
	@Override
	public Class<GetEvent> getActionType()
	{
		return GetEvent.class;
	}

	@Override
	public GetEventResult execute(GetEvent action, ExecutionContext context) throws DispatchException
	{
		ConnectionKey key = action.getConnectionKey();
		Connection connection = ConnectionDriver.getConnection(key);

		BlockingDeque<GwtEvent> eventDeque = connection.getEventDeque();

		final ArrayList<GwtEvent> events = new ArrayList<GwtEvent>();

		do
		{
			try
			{
				events.add(eventDeque.takeFirst());
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		}
		while (!eventDeque.isEmpty());

		return new GetEventResult(events);

	}

	@Override
	public void rollback(GetEvent action, GetEventResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
