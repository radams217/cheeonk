package com.cheeonk.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.cheeonk.server.Connection;
import com.cheeonk.server.ConnectionDriver;
import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.action.ChangePresence;
import com.cheeonk.shared.result.ChangePresenceResult;

public class ChangePresenceHandler implements ActionHandler<ChangePresence, ChangePresenceResult>
{

	@Override
	public Class<ChangePresence> getActionType()
	{
		return ChangePresence.class;
	}

	@Override
	public ChangePresenceResult execute(ChangePresence action, ExecutionContext context) throws DispatchException
	{
		ConnectionKey key = action.getConnectionKey();
		Connection connection = ConnectionDriver.getConnection(key);

		connection.sendPacket(Connection.getPresence(action.getPresence()));

		return new ChangePresenceResult(action.getPresence());
	}

	@Override
	public void rollback(ChangePresence action, ChangePresenceResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
