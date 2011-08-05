package com.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.cheeonk.shared.ConnectionKey;
import com.cheeonk.shared.result.GetEventResult;

public class GetEvent implements Action<GetEventResult>, IKey
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

	@Override
	public ConnectionKey getConnectionKey()
	{
		return key;
	}

}
