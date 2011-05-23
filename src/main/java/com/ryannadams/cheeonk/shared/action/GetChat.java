package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.result.GetChatResult;

public class GetChat implements Action<GetChatResult>
{
	private ConnectionKey key;

	@Deprecated
	public GetChat()
	{

	}

	public GetChat(ConnectionKey key)
	{
		this.key = key;
	}

	public ConnectionKey getConnectionKey()
	{
		return key;
	}

}
