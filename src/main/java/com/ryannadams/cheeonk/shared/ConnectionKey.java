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
	private String connectionID;
	private String host;
	private int port;
	private String userName;
	private String domain;
	private String password;

	/**
	 * Required by the GWT Framework.
	 */
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
	public ConnectionKey(String host, int port, String userName, String domain, String password)
	{
		this.host = host;
		this.port = port;
		this.userName = userName;
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

	/**
	 * @return unique connectionID from the server
	 */
	public String getConnectionID()
	{
		return connectionID;
	}

	public void setConnectionID(String connectionID)
	{
		this.connectionID = connectionID;
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

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
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
		result = prime * result + ((connectionID == null) ? 0 : connectionID.hashCode());
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + port;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		if (connectionID == null)
		{
			if (other.connectionID != null)
				return false;
		}
		else
			if (!connectionID.equals(other.connectionID))
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
		if (userName == null)
		{
			if (other.userName != null)
				return false;
		}
		else
			if (!userName.equals(other.userName))
				return false;
		return true;
	}

	/**
	 * Generate Built in connection keys
	 * 
	 * @return ConnectionKey
	 */
	public static ConnectionKey getCheeonkConnectionKey()
	{
		// gtalk.google.com
		return new ConnectionKey("localhost", 5222, "cheeonk.com");
	}

	// TODO: Add Gchat information here

}
