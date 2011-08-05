package com.cheeonk.shared.buddy;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author radams217
 *         <p>
 *         Defined the contract that all buddy objects in the system must follow
 *         in order to be considered a buddy.
 *         </p>
 */
public interface IBuddy extends IsSerializable
{
	enum Subscription
	{
		NONE, BOTH, TO, FROM, REMOVE;
	}

	/**
	 * @return the unique Jabber ID user@domain.com/resource
	 */
	JabberId getJabberId();

	/**
	 * @param name
	 */
	void setName(String name);

	/**
	 * @return the name of the buddy.
	 */
	String getName();

	/**
	 * @param subscription
	 */
	void setSubscription(Subscription subscription);

	/**
	 * @return subscription status of the buddy.
	 */
	Subscription getSubscription();
}
