package com.ryannadams.cheeonk.shared.result;

import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;

import com.ryannadams.cheeonk.shared.buddy.CheeonkBuddy;

public class GetBuddyListResult implements Result
{
	private ArrayList<CheeonkBuddy> buddyList;

	public GetBuddyListResult()
	{
		this.buddyList = new ArrayList<CheeonkBuddy>();
	}

	public GetBuddyListResult(ArrayList<CheeonkBuddy> buddyList)
	{
		this.buddyList = buddyList;
	}

	public void addBuddy(CheeonkBuddy buddy)
	{
		buddyList.add(buddy);
	}

	public CheeonkBuddy[] getBuddyList()
	{
		return buddyList.toArray(new CheeonkBuddy[buddyList.size()]);
	}
}
