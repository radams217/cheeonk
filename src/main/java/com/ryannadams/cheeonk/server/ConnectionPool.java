package com.ryannadams.cheeonk.server;

import java.util.HashMap;
import java.util.Map;

import com.ryannadams.cheeonk.server.internal.Connection;
import com.ryannadams.cheeonk.shared.ConnectionKey;

public class ConnectionPool
{
	private static ConnectionPool instance;

	public static ConnectionPool getInstance()
	{
		if (instance == null)
		{
			instance = new ConnectionPool();
		}

		return instance;
	}

	private final Map<ConnectionKey, Connection> pool;

	public ConnectionPool()
	{
		pool = new HashMap<ConnectionKey, Connection>();
	}

	public Connection getConnection(ConnectionKey key)
	{
		Connection connection = pool.get(key);

		if (connection == null)
		{
			connection = pool.put(key, new Connection(key));
		}

		return connection;
	}
}
