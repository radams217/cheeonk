package com.ryannadams.cheeonk.shared.result;

import net.customware.gwt.dispatch.shared.Result;

public class SendMessageResult implements Result
{
	private boolean isSent;

	@Deprecated
	public SendMessageResult()
	{

	}

	public SendMessageResult(boolean isSent)
	{
		this.isSent = isSent;
	}

	public boolean isSent()
	{
		return isSent;
	}

}
