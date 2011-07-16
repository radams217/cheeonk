package com.ryannadams.cheeonk.shared.buddy;

public class CheeonkBuddy extends AbstractBuddy
{
	private JabberId jID;
	private String name;
	private CheeonkPresence presence;

	@Deprecated
	public CheeonkBuddy()
	{

	}

	public CheeonkBuddy(JabberId jID)
	{
		this.jID = jID;
		this.name = "";
		this.presence = new CheeonkPresence();
	}

	public CheeonkBuddy(JabberId jID, String name)
	{
		this.jID = jID;
		this.name = name;
		this.presence = new CheeonkPresence();
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
	public CheeonkPresence getPresence()
	{
		return presence;
	}

	@Override
	public void setPresence(CheeonkPresence presence)
	{
		this.presence = presence;
	}

	@Override
	public boolean isAvailable()
	{
		return CheeonkPresence.Type.AVAILABLE.equals(presence.getType());
	}

	@Override
	public boolean isAway()
	{
		return CheeonkPresence.Mode.AWAY.equals(presence.getMode());
	}

}
