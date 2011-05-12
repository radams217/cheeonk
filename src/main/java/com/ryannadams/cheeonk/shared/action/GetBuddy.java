package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.JabberId;
import com.ryannadams.cheeonk.shared.result.GetBuddyResult;

public class GetBuddy implements Action<GetBuddyResult>
{
	public JabberId jabberId;

	@Deprecated
	public GetBuddy()
	{

	}

	public GetBuddy(JabberId jabberId)
	{
		this.jabberId = jabberId;
	}

	public JabberId getJabberId()
	{
		return jabberId;
	}
}
