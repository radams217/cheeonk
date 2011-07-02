package com.ryannadams.cheeonk.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class JabberId implements IsSerializable
{
	private String jabberId;

	public JabberId()
	{

	}

	public JabberId(String jabberId)
	{
		this.jabberId = jabberId;
	}

	@Override
	public String toString()
	{
		return jabberId;
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
