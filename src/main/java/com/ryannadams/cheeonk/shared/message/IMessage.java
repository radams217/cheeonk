package com.ryannadams.cheeonk.shared.message;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.ryannadams.cheeonk.shared.JabberId;

/**
 * @author radams217
 *         <p>
 *         IMessage defines the contract that all classes must follow in order
 *         for the class to be considered a Message within this framework.
 *         </p>
 */
public interface IMessage extends IsSerializable
{
	/**
	 * @return the user to receive the message.
	 */
	JabberId getTo();

	/**
	 * @return the user who sent the message
	 */
	JabberId getFrom();

	/**
	 * @return the body of the message.
	 */
	String getBody();
}
