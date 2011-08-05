package com.cheeonk.server.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.cheeonk.server.Connection;
import com.cheeonk.server.ConnectionDriver;
import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.action.SendMessage;
import com.cheeonk.shared.result.SendMessageResult;

public class SendMessageHandler implements ActionHandler<SendMessage, SendMessageResult>
{
	@Override
	public Class<SendMessage> getActionType()
	{
		return SendMessage.class;
	}

	@Override
	public SendMessageResult execute(SendMessage action, ExecutionContext context) throws DispatchException
	{
		ConnectionKey key = action.getConnectionKey();
		Connection connection = ConnectionDriver.getConnection(key);

		connection.sendMessage(action.getMessage());
		Logger.getLogger("").log(Level.FINER, connection.getUser() + " sending message \"" + action.getMessage() + "\"");

		return new SendMessageResult(action.getMessage());
	}

	@Override
	public void rollback(SendMessage action, SendMessageResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
