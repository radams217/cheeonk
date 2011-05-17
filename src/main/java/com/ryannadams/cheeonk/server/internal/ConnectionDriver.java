package com.ryannadams.cheeonk.server.internal;

import com.ryannadams.cheeonk.shared.ConnectionKey;

public class ConnectionDriver
{
	private static ConnectionPool pool;

	private static ConnectionPool getPool()
	{
		if (pool == null)
		{
			pool = new ConnectionPool();
		}

		return pool;
	}

	public static Connection getConnection(ConnectionKey key)
	{
		return getPool().getConnection(key);
	}

}
