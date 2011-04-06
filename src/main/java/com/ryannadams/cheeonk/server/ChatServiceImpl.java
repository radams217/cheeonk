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
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ryannadams.cheeonk.client.chat.IBuddy;
import com.ryannadams.cheeonk.client.chat.IChat;
import com.ryannadams.cheeonk.client.chat.IMessage;
import com.ryannadams.cheeonk.client.services.ChatService;
import com.ryannadams.cheeonk.server.internal.BuddyWrapperImpl;
import com.ryannadams.cheeonk.server.internal.ChatWrapperImpl;
import com.ryannadams.cheeonk.server.internal.MessageWrapperImpl;
import com.ryannadams.cheeonk.server.resources.ConnectionManager;

@SuppressWarnings("serial")
public class ChatServiceImpl extends RemoteServiceServlet implements
		ChatService, ChatManagerListener, MessageListener, RosterListener
{
	private final ConnectionManager connectionManager = ConnectionManager
			.getInstance();

	// private final XMPPConnection connection = new XMPPConnection(
	// new ConnectionConfiguration("ryannadams.com", 5222));

	private final Map<IChat, List<IMessage>> chatMap = new HashMap<IChat, List<IMessage>>();
	private final Set<IBuddy> buddySet = new HashSet<IBuddy>();

	@Override
	public Boolean login(String username, String password)
	{
		XMPPConnection connection = connectionManager.getConnection();

		try
		{
			connection.login(username, password);
			connection.getChatManager().addChatListener(this);
			connection.getRoster().addRosterListener(this);
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
	public IBuddy[] getBuddyList()
	{
		Roster roster = connectionManager.getConnection().getRoster();

		for (RosterEntry buddy : roster.getEntries())
		{
			buddySet.add(new BuddyWrapperImpl(buddy));
		}

		return buddySet.toArray(new IBuddy[buddySet.size()]);
	}

	@Override
	public void addBuddy(IBuddy buddy)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeBuddy(IBuddy buddy)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void createChat(String recipient)
	{
		connectionManager.getConnection().getChatManager()
				.createChat(recipient, this);
	}

	@Override
	public void sendMessage(IChat key, String message)
	{
		for (IChat chat : chatMap.keySet())
		{
			if (key.getParticipant().equals(chat.getParticipant()))
			{
				try
				{
					((ChatWrapperImpl) chat).getChat().sendMessage(message);
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
	public IMessage[] getMessages(IChat key)
	{
		List<IMessage> messages = chatMap.get(key);

		return messages.toArray(new IMessage[messages.size()]);
	}

	@Override
	public void chatCreated(Chat key, boolean createdLocally)
	{
		chatMap.put(new ChatWrapperImpl(key), new ArrayList<IMessage>());
	}

	@Override
	public void processMessage(Chat key, Message message)
	{
		chatMap.get(new ChatWrapperImpl(key)).add(
				new MessageWrapperImpl(message));
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
