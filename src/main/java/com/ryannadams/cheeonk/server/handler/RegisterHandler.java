package com.ryannadams.cheeonk.server.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.jivesoftware.smack.XMPPException;

import com.ryannadams.cheeonk.server.internal.Connection;
import com.ryannadams.cheeonk.server.internal.ConnectionDriver;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.Register;
import com.ryannadams.cheeonk.shared.result.RegisterResult;

public class RegisterHandler implements ActionHandler<Register, RegisterResult>
{
	@Override
	public Class<Register> getActionType()
	{
		return Register.class;
	}

	@Override
	public RegisterResult execute(Register action, ExecutionContext context) throws DispatchException
	{
		Connection connection = ConnectionDriver.getConnection(ConnectionKey.get());

		try
		{
			connection.connect();
			connection.getAccountManager().createAccount(action.getUsername(), action.getPassword());
			Logger.getLogger("").log(Level.FINER, "Account Created for " + action.getUsername());
		}
		catch (XMPPException e)
		{
			return new RegisterResult(false);
		}
		finally
		{
			connection.disconnect();
		}

		return new RegisterResult(true);
	}

	@Override
	public void rollback(Register action, RegisterResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
