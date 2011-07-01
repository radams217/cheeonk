package com.ryannadams.cheeonk.server.handler;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.jivesoftware.smack.RosterEntry;

import com.ryannadams.cheeonk.server.Connection;
import com.ryannadams.cheeonk.server.ConnectionDriver;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.JabberId;
import com.ryannadams.cheeonk.shared.action.GetBuddyList;
import com.ryannadams.cheeonk.shared.buddy.CheeonkBuddy;
import com.ryannadams.cheeonk.shared.buddy.IBuddy;
import com.ryannadams.cheeonk.shared.result.GetBuddyResult;

public class GetBuddyListHandler implements ActionHandler<GetBuddyList, GetBuddyResult>
{
	@Override
	public Class<GetBuddyList> getActionType()
	{
		return GetBuddyList.class;
	}

	@Override
	public GetBuddyResult execute(GetBuddyList action, ExecutionContext context) throws DispatchException
	{
		ConnectionKey key = action.getConnectionKey();
		Connection connection = ConnectionDriver.getConnection(key);

		ArrayList<IBuddy> buddyList = new ArrayList<IBuddy>();

		for (RosterEntry rosterEntry : connection.getRoster().getEntries())
		{
			buddyList.add(new CheeonkBuddy(new JabberId(rosterEntry.getUser()), rosterEntry.getName()));
		}

		Logger.getLogger("").log(Level.FINER, "Getting BuddyList for " + key.getUsername());
		return new GetBuddyResult(buddyList);
	}

	@Override
	public void rollback(GetBuddyList action, GetBuddyResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
