package com.ryannadams.cheeonk.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.ryannadams.cheeonk.server.internal.Connection;
import com.ryannadams.cheeonk.server.internal.ConnectionDriver;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.GetMessages;
import com.ryannadams.cheeonk.shared.result.GetMessageResult;

public class GetMessagesHandler implements ActionHandler<GetMessages, GetMessageResult>
{
	@Override
	public Class<GetMessages> getActionType()
	{
		return GetMessages.class;
	}

	@Override
	public GetMessageResult execute(GetMessages action, ExecutionContext context) throws DispatchException
	{
		ConnectionKey key = action.getConnectionKey();
		Connection connection = ConnectionDriver.getConnection(key);

		return new GetMessageResult(connection.getChatContainer().getMessages(action.getChat()));
	}

	@Override
	public void rollback(GetMessages action, GetMessageResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
