package com.ryannadams.cheeonk.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.ryannadams.cheeonk.server.ConnectionPool;
import com.ryannadams.cheeonk.server.internal.Connection;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.GetMessages;
import com.ryannadams.cheeonk.shared.result.GetMessagesResult;

public class GetMessagesHandler implements ActionHandler<GetMessages, GetMessagesResult>
{
	@Override
	public Class<GetMessages> getActionType()
	{
		return GetMessages.class;
	}

	@Override
	public GetMessagesResult execute(GetMessages action, ExecutionContext context) throws DispatchException
	{
		ConnectionKey key = action.getConnectionKey();
		Connection connection = ConnectionPool.getInstance().getConnection(key);

		return new GetMessagesResult(connection.getChatContainer().getMessages(action.getChat()));
	}

	@Override
	public void rollback(GetMessages action, GetMessagesResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
