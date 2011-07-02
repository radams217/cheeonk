package com.ryannadams.cheeonk.shared.buddy;

public class CheeonkBuddy extends AbstractBuddy
{
	private JabberId jID;
	private String name;
	private Presence presence;
	private String status;

	@Deprecated
	public CheeonkBuddy()
	{

	}

	public CheeonkBuddy(JabberId jID)
	{
		this.jID = jID;
		this.name = "";
		this.presence = new Presence();
	}

	public CheeonkBuddy(JabberId jID, String name)
	{
		this.jID = jID;
		this.name = name;
		this.presence = new Presence();
	}

	@Override
	public JabberId getJabberId()
	{
		return jID;
	}

	@Override
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public Presence getPresence()
	{
		return presence;
	}

	@Override
	public void setPresence(Presence presence)
	{
		this.presence = presence;
	}

	@Override
	public String getStatus()
	{
		return status;
	}

	@Override
	public void setStatus(String status)
	{
		this.status = status;
	}

	@Override
	public boolean isAvailable()
	{
		return Presence.Mode.AVAILABLE.equals(presence.getMode());
	}

	@Override
	public boolean isAway()
	{
		return Presence.Mode.AWAY.equals(presence.getMode());
	}

}
