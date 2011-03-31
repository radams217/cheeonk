package com.ryannadams.cheeonk.server;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ryannadams.cheeonk.client.services.ChatService;
import com.ryannadams.cheeonk.server.resources.ConnectionManager;

public class ChatServiceImpl extends RemoteServiceServlet implements
		ChatService
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6635882140636994640L;

	@Override
	public Boolean login(String username, String password)
	{
		try
		{
			XMPPConnection connection = ConnectionManager.getInstance()
					.getConnection();

			connection.login(username, password);

			return connection.isAuthenticated();
		}
		catch (XMPPException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Boolean logout()
	{
		try
		{
			ConnectionManager.getInstance().getConnection().disconnect();

			return true;
		}
		catch (XMPPException e)
		{
			e.printStackTrace();
		}

		return false;

	}

	@Override
	public List<String> getBuddyList()
	{
		List<String> buddies = new ArrayList<String>();

		try
		{

			Roster roster = ConnectionManager.getInstance().getConnection()
					.getRoster();

			for (RosterEntry buddy : roster.getEntries())
			{
				buddies.add(buddy.getName());
			}

		}
		catch (XMPPException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return buddies;

	}

	@Override
	public void createChat(String username)
	{
		try
		{

			ConnectionManager.getInstance().getConnection().getChatManager()
					.createChat(username, new MessageListener()
					{

						@Override
						public void processMessage(Chat arg0, Message arg1)
						{
							// TODO Auto-generated method stub

						}
					});

		}
		catch (XMPPException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
