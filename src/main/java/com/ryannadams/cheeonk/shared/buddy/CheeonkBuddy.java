package com.ryannadams.cheeonk.shared.buddy;

import com.ryannadams.cheeonk.shared.JabberId;
import com.ryannadams.cheeonk.shared.Presence;

public class CheeonkBuddy extends AbstractBuddy
{
	private JabberId jID;
	private String name;
	private Presence presence;

	private boolean isAvailable;
	private boolean isAway;

	@Deprecated
	public CheeonkBuddy()
	{

	}

	public CheeonkBuddy(JabberId jID, String name)
	{
		this.jID = jID;
		this.name = name;
		this.isAvailable = false;
		this.isAway = false;
	}

	public CheeonkBuddy(JabberId jID, String name, boolean isAvailable)
	{
		this.jID = jID;
		this.name = name;
		this.isAvailable = isAvailable;
		this.isAway = false;
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
	public boolean isAvailable()
	{
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable)
	{
		this.isAvailable = isAvailable;
	}

	@Override
	public boolean isAway()
	{
		return isAway;
	}

	public void setAway(boolean isAway)
	{
		this.isAway = isAway;
	}

}
