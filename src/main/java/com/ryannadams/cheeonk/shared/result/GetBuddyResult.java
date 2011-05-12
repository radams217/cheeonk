package com.ryannadams.cheeonk.shared.result;

import net.customware.gwt.dispatch.shared.Result;

import com.ryannadams.cheeonk.shared.buddy.CheeonkBuddy;

public class GetBuddyResult implements Result
{
	private CheeonkBuddy buddy;

	@Deprecated
	public GetBuddyResult()
	{

	}

	public GetBuddyResult(CheeonkBuddy buddy)
	{
		this.buddy = buddy;
	}

	public CheeonkBuddy getBuddy()
	{
		return buddy;
	}
}
