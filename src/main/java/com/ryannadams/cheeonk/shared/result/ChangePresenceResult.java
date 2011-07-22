package com.ryannadams.cheeonk.shared.result;

import net.customware.gwt.dispatch.shared.Result;

import com.ryannadams.cheeonk.shared.buddy.CheeonkPresence;

public class ChangePresenceResult implements Result
{
	private CheeonkPresence cheeonkPresence;

	@Deprecated
	public ChangePresenceResult()
	{

	}

	public ChangePresenceResult(CheeonkPresence cheeonkPresence)
	{
		this.cheeonkPresence = cheeonkPresence;
	}

	public CheeonkPresence getPresence()
	{
		return cheeonkPresence;
	}

}
