package com.cheeonk.client.widgets.chat;

import com.cheeonk.shared.message.IMessage;
import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractChatWidget extends Composite implements IChatWidget
{
	private boolean isMinimized;
	private boolean isMaximized;
	private boolean isClosed;

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
	public abstract void addCheeonk(IMessage message);

	@Override
	public abstract void minimize();

	@Override
	public abstract void maximize();

	@Override
	public abstract void close();
}
