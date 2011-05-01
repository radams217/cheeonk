package com.ryannadams.cheeonk.shared.message;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author radams217
 *         <p>
 *         ClientMessage implements IMessage and IsSerializable. This class is
 *         intended to be used on both the client and server side as a way to
 *         transmit Messages from the server to the client. All messages are
 *         serialized and sent over the wire to the client.
 *         </p>
 */
public class ClientMessage implements IMessage, IsSerializable
{
	protected String body;
	protected String to;
	protected String from;

	/**
	 * Necessary for the GWT framework
	 */
	public ClientMessage()
	{
		// Do Nothing
	}

	/**
	 * @param body
	 * @param to
	 * @param from
	 */
	public ClientMessage(String body, String to, String from)
	{
		this.body = body;
		this.to = to;
		this.from = from;
	}

	@Override
	public String getBody()
	{
		return body;
	}

	@Override
	public String getTo()
	{
		return to;
	}

	@Override
	public String getFrom()
	{
		return from;
	}

}
