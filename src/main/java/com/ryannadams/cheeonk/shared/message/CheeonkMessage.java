package com.ryannadams.cheeonk.shared.message;

import com.ryannadams.cheeonk.shared.buddy.JabberId;

/**
 * @author radams217
 *         <p>
 *         ClientMessage implements IMessage and IsSerializable. This class is
 *         intended to be used on both the client and server side as a way to
 *         transmit Messages from the server to the client. All messages are
 *         serialized and sent over the wire to the client.
 *         </p>
 */
public class CheeonkMessage implements IMessage
{
	protected JabberId to;
	protected JabberId from;
	protected String body;

	@Deprecated
	public CheeonkMessage()
	{
		// Do Nothing
	}

	/**
	 * @param body
	 * @param to
	 * @param from
	 */
	public CheeonkMessage(JabberId to, JabberId from, String body)
	{
		this.to = to;
		this.from = from;
		this.body = body;
	}

	@Override
	public JabberId getTo()
	{
		return to;
	}

	@Override
	public JabberId getFrom()
	{
		return from;
	}

	@Override
	public String getBody()
	{
		return body;
	}

}
