package com.ryannadams.cheeonk.shared.result;

import net.customware.gwt.dispatch.shared.Result;

import com.ryannadams.cheeonk.shared.message.CheeonkMessage;

/**
 * @author radams217
 *         <p>
 *         GetMessageResult implements IMessage and Result. This class is
 *         intended to be used on both the client and server side as a way to
 *         transmit Messages from the server to the client. All messages are
 *         serialized and sent over the wire to the client.
 *         </p>
 */
public class GetMessageResult implements Result
{
	private CheeonkMessage message;

	@Deprecated
	public GetMessageResult()
	{

	}

	public GetMessageResult(CheeonkMessage message)
	{
		this.message = message;
	}

	public CheeonkMessage getMessage(CheeonkMessage message)
	{
		return message;
	}

}
