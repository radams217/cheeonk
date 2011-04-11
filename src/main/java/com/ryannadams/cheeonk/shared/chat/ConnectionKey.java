package com.ryannadams.cheeonk.shared.chat;

public class ConnectionKey
{
	private String connectionID;
	private String host;
	private int port;
	private String userName;
	private String domain;
	private String password;

	public ConnectionKey(String host, int port, String userName, String domain,
			String password)
	{
		this.host = host;
		this.port = port;
		this.userName = userName;
		this.domain = domain;
		this.password = password;
	}

	public ConnectionKey(String host, int port, String domain)
	{
		this.host = host;
		this.port = port;
		this.domain = domain;
	}

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
		result = prime * result
				+ ((connectionID == null) ? 0 : connectionID.hashCode());
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + port;
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
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
		} else
			if (!connectionID.equals(other.connectionID))
				return false;
		if (domain == null)
		{
			if (other.domain != null)
				return false;
		} else
			if (!domain.equals(other.domain))
				return false;
		if (host == null)
		{
			if (other.host != null)
				return false;
		} else
			if (!host.equals(other.host))
				return false;
		if (port != other.port)
			return false;
		if (userName == null)
		{
			if (other.userName != null)
				return false;
		} else
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
		return new ConnectionKey("localhost", 5222, "ryannadams.com");
	}

	public static ConnectionKey getGChatConnectionKey()
	{
		return new ConnectionKey("gchat.google.com", 5222, "google.com");
	}

}
