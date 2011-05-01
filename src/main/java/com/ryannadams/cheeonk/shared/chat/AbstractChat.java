package com.ryannadams.cheeonk.shared.chat;

public abstract class AbstractChat implements IChat
{
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj == null)
		{
			return false;
		}

		IChat other = (IChat) obj;

		if (getThreadID() == null)
		{
			if (other.getThreadID() != null)
			{
				return false;
			}
		}
		else
		{
			if (!getThreadID().equals(other.getThreadID()))
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getThreadID() == null) ? 0 : getThreadID().hashCode());
		return result;
	}
}
