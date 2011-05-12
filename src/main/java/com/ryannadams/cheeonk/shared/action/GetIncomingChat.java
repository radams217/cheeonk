package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.result.GetChatResult;

public class GetIncomingChat implements Action<GetChatResult>
{
	private ConnectionKey key;

	@Deprecated
	public GetIncomingChat()
	{

	}

	public GetIncomingChat(ConnectionKey key)
	{
		this.key = key;
	}

	public ConnectionKey getConnectionKey()
	{
		return key;
	}
}
