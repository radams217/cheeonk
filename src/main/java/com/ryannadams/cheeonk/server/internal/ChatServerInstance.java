package com.ryannadams.cheeonk.server.internal;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;

import com.ryannadams.cheeonk.server.internal.containers.BuddyContainer;
import com.ryannadams.cheeonk.server.internal.containers.ChatContainer;
import com.ryannadams.cheeonk.shared.ChatServerKey;

public class ChatServerInstance
{
	private final XMPPConnection connection;
	private final BuddyContainer buddyContainer;
	private final ChatContainer chatContainer;

	public ChatServerInstance(ChatServerKey key)
	{
		this.connection = new XMPPConnection(new ConnectionConfiguration(key.getHost(), key.getPort()));

		this.buddyContainer = new BuddyContainer();
		this.chatContainer = new ChatContainer();
	}

	public void addListeners()
	{
		this.connection.getChatManager().addChatListener(chatContainer);
		this.connection.getRoster().addRosterListener(buddyContainer);
	}

	public void removeListeners()
	{
		this.connection.getChatManager().removeChatListener(chatContainer);
		this.connection.getRoster().removeRosterListener(buddyContainer);
	}

	public XMPPConnection getConnection()
	{
		return connection;
	}

	public BuddyContainer getBuddyContainer()
	{
		return buddyContainer;
	}

	public ChatContainer getChatContainer()
	{
		return chatContainer;
	}

}
