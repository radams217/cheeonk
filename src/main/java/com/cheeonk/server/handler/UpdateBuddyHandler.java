package com.cheeonk.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.cheeonk.server.Connection;
import com.cheeonk.server.ConnectionDriver;
import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.action.UpdateBuddy;
import com.cheeonk.shared.result.UpdateBuddyResult;

public class UpdateBuddyHandler implements ActionHandler<UpdateBuddy, UpdateBuddyResult>
{
	@Override
	public Class<UpdateBuddy> getActionType()
	{
		return UpdateBuddy.class;
	}

	@Override
	public UpdateBuddyResult execute(UpdateBuddy action, ExecutionContext context) throws DispatchException
	{
		ConnectionKey key = action.getConnectionKey();
		Connection connection = ConnectionDriver.getConnection(key);

		connection.getRoster().getEntry(action.getBuddy().getJabberId().getJabberId()).setName(action.getBuddy().getName());

		return new UpdateBuddyResult();
	}

	@Override
	public void rollback(UpdateBuddy action, UpdateBuddyResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
