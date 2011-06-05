package com.ryannadams.cheeonk.server.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.ryannadams.cheeonk.server.internal.Connection;
import com.ryannadams.cheeonk.server.internal.ConnectionDriver;
import com.ryannadams.cheeonk.shared.ConnectionKey;
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
		ConnectionKey key = action.getConnectionKey();
		Connection connection = ConnectionDriver.getConnection(key);

		boolean isAvailable = connection.getRoster().getPresence(action.getJabberId().getJabberId()).isAvailable();

		Logger.getLogger("").log(Level.FINER, action.getJabberId().getJabberId() + "presence check.");

		return new GetBuddyPresenceResult(isAvailable);
	}

	@Override
	public void rollback(GetBuddyPresence action, GetBuddyPresenceResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
