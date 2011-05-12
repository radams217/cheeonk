package com.ryannadams.cheeonk.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.ryannadams.cheeonk.server.ConnectionPool;
import com.ryannadams.cheeonk.server.internal.Connection;
import com.ryannadams.cheeonk.server.internal.wrappers.ChatWrapper;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.GetChat;
import com.ryannadams.cheeonk.shared.result.GetChatResult;

public class CreateChatHandler implements ActionHandler<GetChat, GetChatResult>
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
		Connection connection = ConnectionPool.getInstance().getConnection(key);

		return new GetChatResult(
				new ChatWrapper(connection.getChatManager().createChat(action.getRecipient().getJabberId(), connection.getChatContainer())).getClientChat());
	}

	@Override
	public void rollback(GetChat action, GetChatResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
