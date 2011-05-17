package com.ryannadams.cheeonk.server.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.jivesoftware.smack.Roster.SubscriptionMode;
import org.jivesoftware.smack.XMPPException;

import com.ryannadams.cheeonk.server.internal.Connection;
import com.ryannadams.cheeonk.server.internal.ConnectionDriver;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.Signin;
import com.ryannadams.cheeonk.shared.result.SigninResult;

public class SigninHandler implements ActionHandler<Signin, SigninResult>
{
	@Override
	public Class<Signin> getActionType()
	{
		return Signin.class;
	}

	@Override
	public SigninResult execute(Signin action, ExecutionContext context) throws DispatchException
	{
		ConnectionKey key = action.getConnectionKey();
		Connection connection = ConnectionDriver.getConnection(key);

		try
		{
			// Logger.getLogger("").log(Level.FINE, "Client connected to " +
			// connection.getServiceName() + ":" + connection.getPort());

			connection.login(key.getUserName(), key.getPassword());
			Logger.getLogger("").log(Level.FINE, "Client logged in as " + connection.getUser());

			// For now accept all subscription requests
			connection.getRoster().setSubscriptionMode(SubscriptionMode.accept_all);

			connection.addListeners();
		}
		catch (XMPPException e)
		{
			Logger.getLogger("").log(Level.SEVERE, "Exception while executing " + action.getClass().getName() + ": " + e.getMessage());

			if (connection.isConnected())
			{
				connection.disconnect();
			}
		}

		SigninResult result = new SigninResult();
		result.setConnectionId(connection.getConnectionID());
		result.setConnected(connection.isConnected());
		result.setSignedin(connection.isAuthenticated());

		return result;
	}

	@Override
	public void rollback(Signin action, SigninResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
