package com.ryannadams.cheeonk.server.internal;

import java.util.Collection;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;

import com.ryannadams.cheeonk.server.internal.containers.ChatContainer;
import com.ryannadams.cheeonk.shared.ConnectionKey;

public class Connection extends XMPPConnection implements RosterListener
{
	private final ChatContainer chatContainer;

	public Connection(ConnectionKey key)
	{
		super(new ConnectionConfiguration(key.getHost(), key.getPort()));
		this.chatContainer = new ChatContainer();
	}

	public void addListeners()
	{
		getChatManager().addChatListener(chatContainer);
		getRoster().addRosterListener(this);
	}

	public void removeListeners()
	{
		getChatManager().removeChatListener(chatContainer);
		getRoster().removeRosterListener(this);
	}

	public ChatContainer getChatContainer()
	{
		return chatContainer;
	}

	@Override
	public void entriesAdded(Collection<String> entries)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void entriesDeleted(Collection<String> entries)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void entriesUpdated(Collection<String> entries)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void presenceChanged(Presence presence)
	{
		// TODO Auto-generated method stub

	}

}
