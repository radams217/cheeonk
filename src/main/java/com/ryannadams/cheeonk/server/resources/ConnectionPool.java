package com.ryannadams.cheeonk.server.resources;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;

import com.ryannadams.cheeonk.shared.chat.ConnectionKey;

public class ConnectionPool
{
	private Map<ConnectionKey, XMPPConnection> connectionPool;

	static
	{
		// XMPPConnection.DEBUG_ENABLED = true;
	}

	public ConnectionPool()
	{
		connectionPool = new HashMap<ConnectionKey, XMPPConnection>();
	}

	public XMPPConnection getConnection(ConnectionKey key)
	{
		if (!connectionPool.containsKey(key))
		{
			connectionPool.put(key, new XMPPConnection(
					new ConnectionConfiguration(key.getHost(), key.getPort())));
		}

		return connectionPool.get(key);
	}
}
