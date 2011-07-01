package com.ryannadams.cheeonk.shared.result;

import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;

import com.ryannadams.cheeonk.shared.buddy.CheeonkBuddy;
import com.ryannadams.cheeonk.shared.buddy.IBuddy;

public class GetBuddyResult implements Result
{
	private ArrayList<IBuddy> buddies;

	public GetBuddyResult()
	{
		this.buddies = new ArrayList<IBuddy>();
	}

	public GetBuddyResult(ArrayList<IBuddy> buddies)
	{
		this.buddies = buddies;
	}

	public void addBuddy(CheeonkBuddy buddy)
	{
		buddies.add(buddy);
	}

	public ArrayList<IBuddy> getBuddyList()
	{
		return new ArrayList<IBuddy>(buddies);
	}
}
