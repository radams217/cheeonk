package com.ryannadams.cheeonk.shared.chat;

/**
 * @author radams217
 * 
 *         This abstract chat class should be extended by all chat objects. It
 *         contains the overriden equals and hashcode method. Due to the fact
 *         that server side objects can't be used on the client side that aren't
 *         serializable this is necessary to determine equal elements in the two
 *         different objects both implementing the chat interface through from
 *         class.
 */
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
