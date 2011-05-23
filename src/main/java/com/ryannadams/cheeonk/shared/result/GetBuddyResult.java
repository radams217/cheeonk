package com.ryannadams.cheeonk.shared.result;

import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;

import com.ryannadams.cheeonk.shared.buddy.CheeonkBuddy;

public class GetBuddyResult implements Result
{
	private ArrayList<CheeonkBuddy> buddies;

	public GetBuddyResult()
	{
		this.buddies = new ArrayList<CheeonkBuddy>();
	}

	public GetBuddyResult(ArrayList<CheeonkBuddy> buddies)
	{
		this.buddies = buddies;
	}

	public void addBuddy(CheeonkBuddy buddy)
	{
		buddies.add(buddy);
	}

	public ArrayList<CheeonkBuddy> getBuddyList()
	{
		return new ArrayList<CheeonkBuddy>(buddies);
	}
}
