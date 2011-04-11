package com.ryannadams.cheeonk.client.chat;

public abstract class AbstractChat implements IChat
{
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IChat other = (IChat) obj;
		if (getParticipant() == null)
		{
			if (other.getParticipant() != null)
				return false;
		} else
			if (!getParticipant().equals(other.getParticipant()))
				return false;
		return true;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((getParticipant() == null) ? 0 : getParticipant().hashCode());
		return result;
	}
}
