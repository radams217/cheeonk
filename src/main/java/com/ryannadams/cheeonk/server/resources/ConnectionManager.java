package com.ryannadams.cheeonk.server.resources;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class ConnectionManager
{
	public static ConnectionManager instance;

	public static synchronized ConnectionManager getInstance()
	{
		if (instance == null)
		{
			instance = new ConnectionManager();
		}

		return instance;
	}

	private XMPPConnection connection;

	public XMPPConnection getConnection()
	{
		if (connection == null)
		{
			connection = new XMPPConnection(new ConnectionConfiguration(
					"localhost", 5222));
		}

		if (!connection.isConnected())
		{
			try
			{
				connection.connect();
			}
			catch (XMPPException e)
			{
				e.printStackTrace();
			}
		}

		return connection;
	}

}
