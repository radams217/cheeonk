package com.ryannadams.cheeonk.shared.buddy;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author radams217
 * 
 *         The JabberId can come from the server in two different ways: user@domain
 *         user@domain/resource
 * 
 *         In most cases the resource is irrelevant - perhaps in the future
 *         cheeonk will care - for now the buddy list only needs user@domain,
 *         but when sending a message the recipient will see the sender as
 *         user@domain/resource and that tends to mess up what I am keying
 *         conversations on so when a JabberId is created this class will break
 *         up the parts of the JabberId into it's 1 or 2 parts -
 *         conversations/buddylist etc will be keyed on the JabberId without the
 *         resource.
 */
public class JabberId implements IsSerializable
{
	private String jabberId;
	private String resource;

	@Deprecated
	public JabberId()
	{

	}

	public JabberId(String jabberId)
	{
		this.jabberId = jabberId;
		this.resource = "";

		if (jabberId.contains("/"))
		{
			this.jabberId = jabberId.substring(0, jabberId.indexOf("/"));
			this.resource = jabberId.substring(jabberId.indexOf("/") + 1, jabberId.length());
		}
	}

	public String getJabberId()
	{
		return jabberId;
	}

	public String getResource()
	{
		return resource;
	}

	@Override
	public String toString()
	{
		return getJabberId();// + "/" + getResource();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jabberId == null) ? 0 : jabberId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JabberId other = (JabberId) obj;
		if (jabberId == null)
		{
			if (other.jabberId != null)
				return false;
		}
		else
			if (!jabberId.equals(other.jabberId))
				return false;
		return true;
	}

}
