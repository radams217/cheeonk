package com.ryannadams.cheeonk.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author radams217
 *         <p>
 *         This ChatServerKey is a key used to get Connection, Chat, and Buddy
 *         information from a List on the server side. It compares the
 *         connectID, host, port, and userName as specified in the equals and
 *         hashcode method. A Key matching those criteria are considered equal,
 *         when that is determined it will get the Containers and Connection
 *         objects that it needs to complete the task.
 *         </p>
 */
public class ConnectionKey implements IsSerializable
{
	private static ConnectionKey instance;

	public static ConnectionKey get()
	{
		if (instance == null)
		{
			// Abstract this out to a config file
			instance = new ConnectionKey("cheeonk.com", 5222, "cheeonk.com");
		}

		return instance;
	}

	private String connectionId;
	private String host;
	private int port;
	private String username;
	private String domain;
	private String password;

	@Deprecated
	public ConnectionKey()
	{
		// Do Nothing
	}

	/**
	 * @param host
	 *            Most commonly localhost
	 * @param port
	 *            5222 for chat without SSL
	 * @param userName
	 *            UserName without domain or resource, unique to the domain
	 * @param domain
	 *            for example @cheeonk
	 * @param password
	 */
	public ConnectionKey(String host, int port, String username, String domain, String password)
	{
		this.host = host;
		this.port = port;
		this.username = username;
		this.domain = domain;
		this.password = password;
	}

	/**
	 * @param host
	 * @param port
	 * @param domain
	 */
	public ConnectionKey(String host, int port, String domain)
	{
		this.host = host;
		this.port = port;
		this.domain = domain;
	}

	public void reset()
	{
		connectionId = "";
		username = "";
		password = "";
	}

	/**
	 * @return unique connectionID from the server
	 */
	public String getConnectionId()
	{
		return connectionId;
	}

	public void setConnectionId(String connectionId)
	{
		this.connectionId = connectionId;
	}

	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getDomain()
	{
		return domain;
	}

	public void setDomain(String domain)
	{
		this.domain = domain;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((connectionId == null) ? 0 : connectionId.hashCode());
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + port;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConnectionKey other = (ConnectionKey) obj;
		if (connectionId == null)
		{
			if (other.connectionId != null)
				return false;
		}
		else
			if (!connectionId.equals(other.connectionId))
				return false;
		if (domain == null)
		{
			if (other.domain != null)
				return false;
		}
		else
			if (!domain.equals(other.domain))
				return false;
		if (host == null)
		{
			if (other.host != null)
				return false;
		}
		else
			if (!host.equals(other.host))
				return false;
		if (port != other.port)
			return false;
		if (username == null)
		{
			if (other.username != null)
				return false;
		}
		else
			if (!username.equals(other.username))
				return false;
		return true;
	}

}
