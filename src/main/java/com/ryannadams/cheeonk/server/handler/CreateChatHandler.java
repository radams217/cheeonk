package com.ryannadams.cheeonk.server.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.ryannadams.cheeonk.server.internal.Connection;
import com.ryannadams.cheeonk.server.internal.ConnectionDriver;
import com.ryannadams.cheeonk.server.internal.wrappers.ChatWrapper;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.CreateChat;
import com.ryannadams.cheeonk.shared.result.GetChatResult;

public class CreateChatHandler implements ActionHandler<CreateChat, GetChatResult>
{
	@Override
	public Class<CreateChat> getActionType()
	{
		return CreateChat.class;
	}

	@Override
	public GetChatResult execute(CreateChat action, ExecutionContext context) throws DispatchException
	{
		ConnectionKey key = action.getConnectionKey();
		Connection connection = ConnectionDriver.getConnection(key);

		GetChatResult chatResult = new GetChatResult();
		chatResult.addChat(new ChatWrapper(connection.getChatManager().createChat(action.getRecipient().getJabberId(), connection.getChatContainer()))
				.getClientChat());
		Logger.getLogger("").log(Level.FINER, "Creating Chat for " + key.getUsername() + " to " + action.getRecipient().getJabberId());

		return chatResult;
	}

	@Override
	public void rollback(CreateChat action, GetChatResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
