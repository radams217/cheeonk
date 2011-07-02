package com.ryannadams.cheeonk.shared.buddy;

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

		if (getJabberId() == null)
		{
			if (other.getJabberId() != null)
			{
				return false;
			}
		}
		else
		{
			if (!getJabberId().equals(other.getJabberId()))
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
		result = prime * result + ((getJabberId() == null) ? 0 : getJabberId().hashCode());
		return result;
	}

}
