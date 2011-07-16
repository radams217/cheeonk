package com.ryannadams.cheeonk.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.jivesoftware.smack.packet.Presence;

import com.ryannadams.cheeonk.server.Connection;
import com.ryannadams.cheeonk.server.ConnectionDriver;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.ChangePresence;
import com.ryannadams.cheeonk.shared.result.ChangePresenceResult;

public class ChangePresenceHandler implements ActionHandler<ChangePresence, ChangePresenceResult>
{

	@Override
	public Class<ChangePresence> getActionType()
	{
		return ChangePresence.class;
	}

	@Override
	public ChangePresenceResult execute(ChangePresence action, ExecutionContext context) throws DispatchException
	{
		ConnectionKey key = action.getConnectionKey();
		Connection connection = ConnectionDriver.getConnection(key);

		Presence presence = new Presence(Presence.Type.available, action.getPresence().getStatus(), 1, Presence.Mode.available);

		connection.sendPacket(presence);

		return new ChangePresenceResult();
	}

	@Override
	public void rollback(ChangePresence action, ChangePresenceResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
