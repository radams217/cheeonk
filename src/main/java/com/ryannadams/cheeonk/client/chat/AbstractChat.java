package com.ryannadams.cheeonk.client.chat;

public abstract class AbstractChat implements IChat
{
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof IChat)
		{
			return getParticipant().equals(((IChat) obj).getParticipant());
		}

		return super.equals(obj);
	}

	@Override
	public int hashCode()
	{
		return getParticipant().hashCode();
	}
}
