package com.ryannadams.cheeonk.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.ryannadams.cheeonk.server.internal.Connection;
import com.ryannadams.cheeonk.server.internal.ConnectionDriver;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.SendMessage;
import com.ryannadams.cheeonk.shared.result.SendMessageResult;

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

		connection.getChatContainer().sendMessage(action.getChat(), action.getMessage());

		return new SendMessageResult(true);
	}

	@Override
	public void rollback(SendMessage action, SendMessageResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
