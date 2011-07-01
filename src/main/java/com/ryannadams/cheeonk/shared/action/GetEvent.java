package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.result.GetEventResult;

public class GetEvent implements Action<GetEventResult>
{
	private ConnectionKey key;

	@Deprecated
	public GetEvent()
	{

	}

	public GetEvent(ConnectionKey key)
	{
		this.key = key;
	}

	public ConnectionKey getConnectionKey()
	{
		return key;
	}

}
