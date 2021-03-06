package com.cheeonk.server.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.cheeonk.server.Connection;
import com.cheeonk.server.ConnectionDriver;
import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.action.Signout;
import com.cheeonk.shared.result.SignoutResult;

public class SignoutHandler implements ActionHandler<Signout, SignoutResult>
{
	@Override
	public Class<Signout> getActionType()
	{
		return Signout.class;
	}

	@Override
	public SignoutResult execute(Signout action, ExecutionContext context) throws DispatchException
	{
		ConnectionKey key = action.getConnectionKey();
		Connection connection = ConnectionDriver.getConnection(key);

		connection.removeListeners();
		connection.disconnect();

		Logger.getLogger("").log(Level.FINER, "Client logged out as " + connection.getUser());

		return new SignoutResult(!connection.isConnected());
	}

	@Override
	public void rollback(Signout action, SignoutResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
