package com.ryannadams.cheeonk.client.buddy;

public abstract class AbstractBuddy implements IBuddy
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

		IBuddy other = (IBuddy) obj;

		if (getJID() == null)
		{
			if (other.getJID() != null)
			{
				return false;
			}
		}
		else
		{
			if (!getJID().equals(other.getJID()))
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
		result = prime * result + ((getJID() == null) ? 0 : getJID().hashCode());
		return result;
	}

}
