package com.ryannadams.cheeonk.server.handler;

import java.util.HashMap;
import java.util.Map;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.jivesoftware.smack.XMPPException;

import com.ryannadams.cheeonk.server.Connection;
import com.ryannadams.cheeonk.server.ConnectionDriver;
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
		Map<String, String> accountAttributes = new HashMap<String, String>();

		accountAttributes.put("name", action.getName());
		accountAttributes.put("email", action.getEmail());

		try
		{
			connection.connect();
			connection.getAccountManager().createAccount(action.getUsername(), action.getPassword(), accountAttributes);
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
