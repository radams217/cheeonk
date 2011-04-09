package com.ryannadams.cheeonk.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ryannadams.cheeonk.client.chat.ClientBuddy;
import com.ryannadams.cheeonk.client.chat.ClientChat;
import com.ryannadams.cheeonk.client.chat.ClientMessage;
import com.ryannadams.cheeonk.client.services.ChatService;
import com.ryannadams.cheeonk.server.internal.BuddyWrapper;
import com.ryannadams.cheeonk.server.internal.ChatWrapper;
import com.ryannadams.cheeonk.server.internal.MessageWrapper;
import com.ryannadams.cheeonk.server.resources.ConnectionManager;

@SuppressWarnings("serial")
public class ChatServiceImpl extends RemoteServiceServlet implements
		ChatService, ChatManagerListener, MessageListener, RosterListener
{
	private final ConnectionManager connectionManager = ConnectionManager
			.getInstance();

	private final Map<ChatWrapper, List<MessageWrapper>> chatMap = new HashMap<ChatWrapper, List<MessageWrapper>>();
	private final Set<BuddyWrapper> buddySet = new HashSet<BuddyWrapper>();

	@Override
	public Boolean login(String username, String password)
	{
		XMPPConnection connection = connectionManager.getConnection();

		try
		{
			connection.login(username, password);
			connection.getChatManager().addChatListener(this);
			connection.getRoster().addRosterListener(this);

			for (RosterEntry rosterEntry : connection.getRoster().getEntries())
			{
				buddySet.add(new BuddyWrapper(rosterEntry));
			}

		}
		catch (XMPPException e)
		{
			e.printStackTrace();
		}

		return connection.isAuthenticated();
	}

	@Override
	public Boolean logout()
	{
		XMPPConnection connection = connectionManager.getConnection();
		connection.getChatManager().removeChatListener(this);
		connection.getRoster().removeRosterListener(this);
		connection.disconnect();

		return !connection.isConnected();
	}

	@Override
	public ClientBuddy[] getBuddyList()
	{
		List<ClientBuddy> buddyList = new ArrayList<ClientBuddy>();

		for (BuddyWrapper buddy : buddySet)
		{
			if (!buddy.isTransmitted())
			{
				buddyList.add(buddy.getClientBuddy());
				buddy.setTransmitted(true);
			}
		}

		return buddyList.toArray(new ClientBuddy[buddyList.size()]);
	}

	@Override
	public void addBuddy(ClientBuddy buddy)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeBuddy(ClientBuddy buddy)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public ClientBuddy[] pollBuddyUpdates()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientChat createChat(String recipient)
	{
		return new ChatWrapper(connectionManager.getConnection()
				.getChatManager().createChat(recipient, this)).getClientChat();
	}

	@Override
	public ClientChat[] pollIncomingChats()
	{
		System.out.println("DEBUG: Polling for Incoming Chats");

		List<ClientChat> newChatList = new ArrayList<ClientChat>();

		for (ChatWrapper chat : chatMap.keySet())
		{
			if (!chat.isTransmitted())
			{
				System.out.println("DEBUG: Chat with " + chat.getParticipant()
						+ " found.");
				newChatList.add(chat.getClientChat());
				chat.setTransmitted(true);
			}
		}

		return newChatList.toArray(new ClientChat[newChatList.size()]);
	}

	@Override
	public void sendMessage(ClientChat key, String message)
	{
		System.out.println("DEBUG: Sending Outgoing Message to "
				+ key.getParticipant() + ". [" + message + "]");

		// Store local message sent to message list?
		for (ChatWrapper chat : chatMap.keySet())
		{
			if (key.getParticipant().equals(chat.getParticipant()))
			{
				try
				{
					chat.getChat().sendMessage(message);
				}
				catch (XMPPException e)
				{
					e.printStackTrace();
				}

				break;
			}
		}

	}

	@Override
	public ClientMessage[] getMessages(ClientChat key)
	{
		List<ClientMessage> messageList = new ArrayList<ClientMessage>();

		// Do in reverse order, break when isTransmitted is true will save
		// looping through the entire array
		for (MessageWrapper message : chatMap.get(key))
		{
			messageList.add(message.getClientMessage());
		}

		return messageList.toArray(new ClientMessage[messageList.size()]);
	}

	@Override
	public ClientMessage[] pollIncomingMessages(ClientChat key)
	{
		System.out.println("DEBUG: Polling for Incoming Messages");

		List<ClientMessage> newMessageList = new ArrayList<ClientMessage>();

		// Do in reverse order, break when isTransmitted is true will save
		// looping through the entire array
		for (MessageWrapper message : chatMap.get(key))
		{
			if (!message.isTransmitted())
			{
				System.out.println("DEBUG: Message from "
						+ message.getMessage().getFrom() + " received. ["
						+ message.getMessage().getBody() + "]");
				newMessageList.add(message.getClientMessage());

				message.setTransmitted(true);
			}
		}

		return newMessageList.toArray(new ClientMessage[newMessageList.size()]);
	}

	@Override
	public void chatCreated(Chat key, boolean createdLocally)
	{
		ChatWrapper chat = new ChatWrapper(key);
		chat.setTransmitted(createdLocally);

		chatMap.put(chat, new ArrayList<MessageWrapper>());
	}

	@Override
	public void processMessage(Chat key, Message message)
	{
		System.out.println("DEBUG: Adding Message from " + message.getFrom()
				+ " to the Queue. [" + message.getBody() + "]");
		chatMap.get(new ChatWrapper(key)).add(new MessageWrapper(message));
	}

	@Override
	public void entriesAdded(Collection<String> arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void entriesDeleted(Collection<String> arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void entriesUpdated(Collection<String> arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void presenceChanged(Presence arg0)
	{
		// TODO Auto-generated method stub

	}

}
