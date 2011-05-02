package com.ryannadams.cheeonk.server;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster.SubscriptionMode;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ryannadams.cheeonk.client.services.ChatService;
import com.ryannadams.cheeonk.server.internal.ChatServerInstance;
import com.ryannadams.cheeonk.server.internal.wrappers.ChatWrapper;
import com.ryannadams.cheeonk.shared.ChatServerKey;
import com.ryannadams.cheeonk.shared.buddy.ClientBuddy;
import com.ryannadams.cheeonk.shared.chat.ClientChat;
import com.ryannadams.cheeonk.shared.message.ClientMessage;

@SuppressWarnings("serial")
public class ChatServiceImpl extends RemoteServiceServlet implements ChatService
{
	// private final Logger logger;
	private final Map<ChatServerKey, ChatServerInstance> chatServerInstances;

	public ChatServiceImpl()
	{
		// logger = LoggerFactory.getLogger(ChatServiceImpl.class);
		chatServerInstances = new HashMap<ChatServerKey, ChatServerInstance>();
	}

	@Override
	public String login(ChatServerKey key)
	{
		if (!chatServerInstances.containsKey(key))
		{
			chatServerInstances.put(key, new ChatServerInstance(key));
		}

		XMPPConnection connection = chatServerInstances.get(key).getConnection();

		try
		{
			connection.connect();
			connection.login(key.getUserName(), key.getPassword());

			// For now accept all suscription requests
			connection.getRoster().setSubscriptionMode(SubscriptionMode.accept_all);

			chatServerInstances.get(key).addListeners();

			for (RosterEntry rosterEntry : connection.getRoster().getEntries())
			{
				chatServerInstances.get(key).getBuddyContainer().addBuddy(rosterEntry, connection.getRoster().getPresence(rosterEntry.getUser()));
				System.out.println(key.getUserName() + " just signed in adding " + rosterEntry.getUser() + " isAvailable = "
						+ connection.getRoster().getPresence(rosterEntry.getUser()).isAvailable());
			}
		}
		catch (XMPPException e)
		{
			// e.printStackTrace();

			// Most likely this is a login error
			// If the user credentials are incorrect it throws and exception
			// which I think is stupid
			// Disconnect something went wrong
			if (connection.isConnected())
			{
				connection.disconnect();
			}

			return null;
		}

		// key.setConnectionID(connection.getConnectionID());
		// if not connected this will be null
		return connection.getConnectionID();
	}

	@Override
	public Boolean logout(ChatServerKey key)
	{
		ChatServerInstance instance = chatServerInstances.get(key);
		instance.removeListeners();

		XMPPConnection connection = instance.getConnection();
		connection.disconnect();

		chatServerInstances.remove(key);

		return !connection.isConnected();
	}

	@Override
	public ClientBuddy[] getBuddyList(ChatServerKey key)
	{
		return chatServerInstances.get(key).getBuddyContainer().getBuddyList();
	}

	@Override
	public void addBuddy(ChatServerKey key, ClientBuddy buddy)
	{
		// chatServerInstances.get(key).getBuddyContainer().addBuddy(rosterEntry);
	}

	@Override
	public void removeBuddy(ChatServerKey key, ClientBuddy buddy)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public ClientChat createChat(ChatServerKey key, String recipient)
	{
		ChatServerInstance instance = chatServerInstances.get(key);

		return new ChatWrapper(instance.getConnection().getChatManager().createChat(recipient, instance.getChatContainer())).getClientChat();
	}

	@Override
	public ClientChat[] getIncomingChats(ChatServerKey key)
	{
		return chatServerInstances.get(key).getChatContainer().getIncomingChats();
	}

	@Override
	public void sendMessage(ChatServerKey key, ClientChat chat, String message)
	{
		System.out.println("DEBUG: Sending Outgoing Message to " + chat.getThreadID() + ". [" + message + "]");

		chatServerInstances.get(key).getChatContainer().sendMessage(chat, message);
	}

	@Override
	public ClientMessage[] getMessages(ChatServerKey key, ClientChat chatKey)
	{
		return chatServerInstances.get(key).getChatContainer().getMessages(chatKey);
	}

	@Override
	public Boolean register(String username, String password)
	{
		XMPPConnection connection = new XMPPConnection(new ConnectionConfiguration(ChatServerKey.getCheeonkConnectionKey().getHost(), ChatServerKey
				.getCheeonkConnectionKey().getPort()));

		try
		{
			connection.connect();
			connection.getAccountManager().createAccount(username, password);
			connection.disconnect();
		}
		catch (XMPPException e)
		{
			connection.disconnect();
			return false;
		}

		return true;

	}
}
