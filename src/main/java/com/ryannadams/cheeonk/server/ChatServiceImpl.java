package com.ryannadams.cheeonk.server;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ryannadams.cheeonk.client.chat.ClientBuddy;
import com.ryannadams.cheeonk.client.chat.ClientChat;
import com.ryannadams.cheeonk.client.chat.ClientMessage;
import com.ryannadams.cheeonk.client.services.ChatService;
import com.ryannadams.cheeonk.server.internal.ChatServerInstance;
import com.ryannadams.cheeonk.server.internal.ChatWrapper;
import com.ryannadams.cheeonk.shared.chat.ChatServerKey;

@SuppressWarnings("serial")
public class ChatServiceImpl extends RemoteServiceServlet implements
		ChatService
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

		XMPPConnection connection = chatServerInstances.get(key)
				.getConnection();

		try
		{
			connection.connect();
			chatServerInstances.get(key).addListeners();
			connection.login(key.getUserName(), key.getPassword());

			for (RosterEntry rosterEntry : connection.getRoster().getEntries())
			{
				chatServerInstances.get(key).getBuddyContainer()
						.addBuddy(rosterEntry);
			}
		}
		catch (XMPPException e)
		{
			e.printStackTrace();
		}

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

		return !connection.isConnected();
	}

	@Override
	public ClientBuddy[] getBuddyList(ChatServerKey key)
	{
		return chatServerInstances.get(key).getBuddyContainer()
				.getBuddyList(key);
	}

	@Override
	public void addBuddy(ChatServerKey key, ClientBuddy buddy)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeBuddy(ChatServerKey key, ClientBuddy buddy)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public ClientBuddy[] getBuddyUpdates(ChatServerKey key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientChat createChat(ChatServerKey key, String recipient)
	{
		ChatServerInstance instance = chatServerInstances.get(key);

		return new ChatWrapper(instance.getConnection().getChatManager()
				.createChat(recipient, instance.getChatContainer()))
				.getClientChat();
	}

	@Override
	public ClientChat[] getIncomingChats(ChatServerKey key)
	{
		System.out.println("DEBUG: Polling for Incoming Chats");

		return chatServerInstances.get(key).getChatContainer()
				.getIncomingChats();
	}

	@Override
	public void sendMessage(ChatServerKey key, ClientChat chat, String message)
	{
		System.out.println("DEBUG: Sending Outgoing Message to "
				+ chat.getParticipant() + ". [" + message + "]");

		chatServerInstances.get(key).getChatContainer()
				.sendMessage(chat, message);
	}

	@Override
	public ClientMessage[] getMessages(ChatServerKey key, ClientChat chatKey)
	{

		return chatServerInstances.get(key).getChatContainer()
				.getMessages(chatKey);
	}

}
