package com.ryannadams.cheeonk.server;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jivesoftware.smack.XMPPException;

import com.ryannadams.cheeonk.shared.ConnectionKey;

public class ConnectionPool
{
	private final Map<ConnectionKey, Connection> connections;
	private final ConnectionReaper reaper;
	private final Logger logger;

	public ConnectionPool()
	{
		connections = new HashMap<ConnectionKey, Connection>();
		reaper = new ConnectionReaper(this);
		logger = Logger.getLogger("");
	}

	public synchronized void reapConnections()
	{
		logger.log(Level.FINEST, "Reaping Connections");

		for (ConnectionKey key : connections.keySet())
		{
			if (!connections.get(key).isConnected())
			{
				removeConnection(key);
			}
		}

	}

	public synchronized void closeConnections()
	{
		for (ConnectionKey key : connections.keySet())
		{
			if (connections.get(key).isConnected())
			{
				connections.get(key).disconnect();
			}

			removeConnection(key);
		}

	}

	private synchronized void removeConnection(ConnectionKey key)
	{
		connections.remove(key);
	}

	public synchronized Connection getConnection(ConnectionKey key)
	{
		Connection connection = connections.get(key);

		if (connection == null)
		{
			try
			{
				connection = new Connection(key);
				connection.connect();

				key.setConnectionId(connection.getConnectionID());

				connections.put(key, connection);
			}
			catch (XMPPException e)
			{
				if (connection.isConnected())
				{
					connection.disconnect();
				}
			}

		}

		return connection;
	}

	public synchronized void returnConnection(Connection connection)
	{
		connection.disconnect();
	}

	private class ConnectionReaper// extends Timer
	{
		private final int DELAY = 300000;
		private final ConnectionPool pool;

		ConnectionReaper(ConnectionPool pool)
		{
			this.pool = pool;
			// this.scheduleRepeating(DELAY);
		}

		// @Override
		public void run()
		{
			pool.reapConnections();
		}
	}
}
