package com.ryannadams.cheeonk.server.handler;

import java.util.ArrayList;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.jivesoftware.smack.RosterEntry;

import com.ryannadams.cheeonk.server.internal.Connection;
import com.ryannadams.cheeonk.server.internal.ConnectionDriver;
import com.ryannadams.cheeonk.server.internal.wrappers.BuddyWrapper;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.action.GetBuddyList;
import com.ryannadams.cheeonk.shared.buddy.CheeonkBuddy;
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

		ArrayList<CheeonkBuddy> buddyList = new ArrayList<CheeonkBuddy>();

		for (RosterEntry rosterEntry : connection.getRoster().getEntries())
		{
			buddyList.add(new BuddyWrapper(rosterEntry).getClientBuddy());
		}

		return new GetBuddyResult(buddyList);
	}

	@Override
	public void rollback(GetBuddyList action, GetBuddyResult result, ExecutionContext context) throws DispatchException
	{
		// TODO Auto-generated method stub

	}

}
