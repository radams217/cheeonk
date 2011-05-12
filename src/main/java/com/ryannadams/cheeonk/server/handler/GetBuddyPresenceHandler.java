package com.ryannadams.cheeonk.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.ryannadams.cheeonk.shared.Presence;
import com.ryannadams.cheeonk.shared.action.GetBuddyPresence;
import com.ryannadams.cheeonk.shared.result.GetBuddyPresenceResult;

public class GetBuddyPresenceHandler implements ActionHandler<GetBuddyPresence, GetBuddyPresenceResult>
{
	@Override
	public Class<GetBuddyPresence> getActionType()
	{
		return GetBuddyPresence.class;
	}

	@Override
	public GetBuddyPresenceResult execute(GetBuddyPresence action, ExecutionContext context) throws DispatchException
	{
		GetBuddyPresenceResult result = new GetBuddyPresenceResult(new Presence("test"));

		return result;
	}

	@Override
	public void rollback(GetBuddyPresence arg0, GetBuddyPresenceResult arg1, ExecutionContext arg2) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
