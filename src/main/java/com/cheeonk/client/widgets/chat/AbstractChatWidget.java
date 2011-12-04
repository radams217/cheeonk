package com.cheeonk.client.widgets.chat;

import com.cheeonk.shared.buddy.IBuddy;
import com.cheeonk.shared.message.IMessage;
import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractChatWidget extends Composite implements IChatWidget
{
	private boolean isMinimized;
	private boolean isMaximized;
	private boolean isClosed;

	public AbstractChatWidget()
	{
		setMinimized(true);
		setMaximized(false);
		setClosed(false);
	}

	public void setMinimized(boolean isMinimized)
	{
		this.isMinimized = isMinimized;
	}

	public void setMaximized(boolean isMaximized)
	{
		this.isMaximized = isMaximized;
	}

	public void setClosed(boolean isClosed)
	{
		this.isClosed = isClosed;
	}

	@Override
	public boolean isMinimized()
	{
		return isMinimized;
	}

	@Override
	public boolean isMaximized()
	{
		return isMaximized;
	}

	@Override
	public boolean isClosed()
	{
		return isClosed;
	}

	@Override
	public void minimize()
	{
		setMinimized(true);
		setMaximized(false);
		setClosed(false);
	}

	@Override
	public void maximize()
	{
		setMinimized(false);
		setMaximized(true);
		setClosed(false);
	}

	@Override
	public void close()
	{
		setMinimized(false);
		setMaximized(false);
		setClosed(true);
	}

	@Override
	public abstract void addMessage(IMessage message);

	@Override
	public abstract IBuddy getParticipant();
}
