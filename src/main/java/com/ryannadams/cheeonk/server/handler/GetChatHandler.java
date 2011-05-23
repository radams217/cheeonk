package com.ryannadams.cheeonk.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.ryannadams.cheeonk.server.internal.Connection;
import com.ryannadams.cheeonk.server.internal.ConnectionDriver;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.GetChat;
import com.ryannadams.cheeonk.shared.result.GetChatResult;

public class GetChatHandler implements ActionHandler<GetChat, GetChatResult>
{
	@Override
	public Class<GetChat> getActionType()
	{
		return GetChat.class;
	}

	@Override
	public GetChatResult execute(GetChat action, ExecutionContext context) throws DispatchException
	{
		ConnectionKey key = action.getConnectionKey();
		Connection connection = ConnectionDriver.getConnection(key);

		return new GetChatResult(connection.getChatContainer().getChats());
	}

	@Override
	public void rollback(GetChat action, GetChatResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
