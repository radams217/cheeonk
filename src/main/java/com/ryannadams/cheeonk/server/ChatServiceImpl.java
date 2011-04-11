package com.ryannadams.cheeonk.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ryannadams.cheeonk.client.chat.ClientBuddy;
import com.ryannadams.cheeonk.client.chat.ClientChat;
import com.ryannadams.cheeonk.client.chat.ClientMessage;
import com.ryannadams.cheeonk.client.services.ChatService;
import com.ryannadams.cheeonk.server.internal.BuddyContainer;
import com.ryannadams.cheeonk.server.internal.ChatContainer;
import com.ryannadams.cheeonk.server.internal.ChatWrapper;
import com.ryannadams.cheeonk.server.resources.ConnectionPool;
import com.ryannadams.cheeonk.shared.chat.ConnectionKey;

@SuppressWarnings("serial")
public class ChatServiceImpl extends RemoteServiceServlet implements
		ChatService
{
	private final ConnectionPool connectionPool = new ConnectionPool();

	private final Map<ConnectionKey, ChatContainer> chatContainers = new HashMap<ConnectionKey, ChatContainer>();
	private final Map<ConnectionKey, BuddyContainer> buddyContainers = new HashMap<ConnectionKey, BuddyContainer>();

	@Override
	public String login(ConnectionKey connectionKey)
	{
		XMPPConnection connection = connectionPool.getConnection(connectionKey);
		ChatContainer chatContainer = new ChatContainer();

		try
		{
			connection.connect();
			connection.login(connectionKey.getUserName(),
					connectionKey.getPassword());
			connection.getChatManager().addChatListener(chatContainer);
			chatContainers.put(connectionKey, chatContainer);

			BuddyContainer buddyContainer = new BuddyContainer(connection
					.getRoster().getEntries());

			buddyContainers.put(connectionKey, buddyContainer);

			connection.getRoster().addRosterListener(buddyContainer);

		}
		catch (XMPPException e)
		{
			e.printStackTrace();
		}

		// if not connected this will be null
		return connection.getConnectionID();
	}

	@Override
	public Boolean logout(ConnectionKey connectionKey)
	{
		XMPPConnection connection = connectionPool.getConnection(connectionKey);

		connection.getChatManager().removeChatListener(
				chatContainers.get(connectionKey));
		connection.getRoster().removeRosterListener(
				buddyContainers.get(connectionKey));
		connection.disconnect();

		return !connection.isConnected();
	}

	@Override
	public ClientBuddy[] getBuddyList(ConnectionKey connectionKey)
	{
		List<ClientBuddy> buddyList = new ArrayList<ClientBuddy>();

		// for (BuddyWrapper buddy : buddySet)
		// {
		// if (!buddy.isTransmitted())
		// {
		// buddyList.add(buddy.getClientBuddy());
		// buddy.setTransmitted(true);
		// }
		// }

		return buddyList.toArray(new ClientBuddy[buddyList.size()]);
	}

	@Override
	public void addBuddy(ConnectionKey connectionKey, ClientBuddy buddy)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeBuddy(ConnectionKey connectionKey, ClientBuddy buddy)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public ClientBuddy[] getBuddyUpdates(ConnectionKey connectionKey)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientChat createChat(ConnectionKey connectionKey, String recipient)
	{
		XMPPConnection connection = connectionPool.getConnection(connectionKey);

		return new ChatWrapper(connection.getChatManager().createChat(
				recipient, chatContainers.get(connectionKey))).getClientChat();
	}

	@Override
	public ClientChat[] getIncomingChats(ConnectionKey connectionKey)
	{
		System.out.println("DEBUG: Polling for Incoming Chats");

		return new ClientChat[0];
	}

	@Override
	public void sendMessage(ConnectionKey connectionKey, ClientChat chat,
			String message)
	{
		System.out.println("DEBUG: Sending Outgoing Message to "
				+ chat.getParticipant() + ". [" + message + "]");

	}

	@Override
	public ClientMessage[] getMessages(ConnectionKey connectionKey,
			ClientChat chatKey)
	{
		List<ClientMessage> newMessageList = new ArrayList<ClientMessage>();

		return newMessageList.toArray(new ClientMessage[newMessageList.size()]);
	}

}
