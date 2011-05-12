package com.ryannadams.cheeonk.shared.result;

import com.ryannadams.cheeonk.shared.Presence;

import net.customware.gwt.dispatch.shared.Result;


public class GetBuddyPresenceResult implements Result
{
	private Presence presence;

	/**
	 * Default Constructor only used for serialization
	 */
	@Deprecated
	public GetBuddyPresenceResult()
	{
	}

	public GetBuddyPresenceResult(Presence presence)
	{
		this.presence = presence;
	}

	public Presence getPresence()
	{
		return presence;
	}

}
