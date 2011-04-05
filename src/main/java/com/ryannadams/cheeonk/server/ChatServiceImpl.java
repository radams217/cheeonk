package com.ryannadams.cheeonk.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ryannadams.cheeonk.client.services.ChatService;
import com.ryannadams.cheeonk.server.resources.ChatManager;
import com.ryannadams.cheeonk.server.resources.ConnectionManager;

@SuppressWarnings("serial")
public class ChatServiceImpl extends RemoteServiceServlet implements
		ChatService, ChatManagerListener, MessageListener
{
	private static final ConnectionManager connectionManager = ConnectionManager
			.getInstance();
	private static final ChatManager chatManager = ChatManager.getInstance();

	private Map<Chat, List<Message>> chats = new HashMap<Chat, List<Message>>();

	@Override
	public String[] getMessages()
	{

	}

	@Override
	public void processMessage(Chat chat, Message message)
	{
		chats.get(chat).add(message);
	}

	@Override
	public Boolean login(String username, String password)
	{
		XMPPConnection connection = connectionManager.getConnection();

		try
		{
			connection.login(username, password);
			connection.getChatManager().addChatListener(this);
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
		connection.disconnect();

		return !connection.isConnected();
	}

	@Override
	public List<String> getBuddyList()
	{
		List<String> buddies = new ArrayList<String>();

		Roster roster = connectionManager.getConnection().getRoster();

		for (RosterEntry buddy : roster.getEntries())
		{
			buddies.add(buddy.getName());
		}

		return buddies;
	}

	@Override
	public void createChat(String recipient)
	{

	}

	@Override
	public void sendMessage(String message)
	{

	}

	@Override
	public void chatCreated(Chat chat, boolean createdLocally)
	{
		// TODO Auto-generated method stub

	}

}
