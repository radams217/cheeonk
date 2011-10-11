package com.cheeonk.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import com.cheeonk.server.Connection;
import com.cheeonk.server.ConnectionDriver;
import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.action.AddBuddy;
import com.cheeonk.shared.result.AddBuddyResult;

public class AddBuddyHandler implements ActionHandler<AddBuddy, AddBuddyResult>
{
	@Override
	public Class<AddBuddy> getActionType()
	{
		return AddBuddy.class;
	}

	@Override
	public AddBuddyResult execute(AddBuddy action, ExecutionContext context) throws DispatchException
	{
		ConnectionKey key = action.getConnectionKey();
		Connection connection = ConnectionDriver.getConnection(key);

		try
		{
			connection.getRoster().createEntry(action.getBuddy().getJabberId().getJabberId(), action.getBuddy().getName(), null);

			Presence presence = new Presence(Presence.Type.subscribed);
			presence.setTo(action.getBuddy().getJabberId().getJabberId());
			connection.sendPacket(presence);
		}
		catch (XMPPException e)
		{
			e.printStackTrace();
		}

		return new AddBuddyResult();
	}

	@Override
	public void rollback(AddBuddy action, AddBuddyResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
