package com.ryannadams.cheeonk.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.JabberId;
import com.ryannadams.cheeonk.shared.result.GetChatResult;

public class GetChat implements Action<GetChatResult>
{
	private ConnectionKey key;
	private JabberId recipient;

	@Deprecated
	public GetChat()
	{

	}

	public GetChat(ConnectionKey key, JabberId recipient)
	{
		this.key = key;
		this.recipient = recipient;
	}

	public ConnectionKey getConnectionKey()
	{
		return key;
	}

	public JabberId getRecipient()
	{
		return recipient;
	}
}
