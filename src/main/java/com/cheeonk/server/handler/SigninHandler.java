package com.cheeonk.server.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.jivesoftware.smack.Roster.SubscriptionMode;
import org.jivesoftware.smack.XMPPException;

import com.cheeonk.server.Connection;
import com.cheeonk.server.ConnectionDriver;
import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.action.Signin;
import com.cheeonk.shared.buddy.JabberId;
import com.cheeonk.shared.result.SigninResult;

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

		SigninResult result = new SigninResult();

		try
		{
			result.setConnected(connection.isConnected());
			connection.login(action.getUsername(), action.getPassword());
			Logger.getLogger("").log(Level.FINER, "Client logged in as " + connection.getUser());

			// For now accept all subscription requests
			connection.getRoster().setSubscriptionMode(SubscriptionMode.accept_all);
			connection.processRoster();

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

		// Change this perhaps
		result.setJabberId(new JabberId(action.getUsername() + "@" + key.getDomain()));
		result.setConnectionId(connection.getConnectionID());
		result.setSignedin(connection.isAuthenticated());

		return result;
	}

	@Override
	public void rollback(Signin action, SigninResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
