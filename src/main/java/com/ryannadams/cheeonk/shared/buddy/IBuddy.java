package com.ryannadams.cheeonk.shared.buddy;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.ryannadams.cheeonk.shared.JabberId;

/**
 * @author radams217
 *         <p>
 *         Defined the contract that all buddy objects in the system must follow
 *         in order to be considered a buddy.
 *         </p>
 */
public interface IBuddy extends IsSerializable
{
	/**
	 * @return the unique Jabber ID user@domain.com/resource
	 */
	JabberId getJID();

	/**
	 * @return the name of the user.
	 */
	String getName();

	/**
	 * @return true/false depending on if the user is available on the server.
	 */
	boolean isAvailable();

	/**
	 * @return true/false depending on if the user is available but away or not
	 *         available.
	 */
	boolean isAway();
}
