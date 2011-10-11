package com.cheeonk.shared.buddy;


public class CheeonkBuddy extends AbstractBuddy
{
	private JabberId jID;
	private String name;
	private Subscription subscription;

	// private List<String> groups;

	@Deprecated
	public CheeonkBuddy()
	{

	}

	public CheeonkBuddy(JabberId jID, String name)
	{
		this.jID = jID;
		this.name = name;
		this.subscription = Subscription.NONE;
	}

	public CheeonkBuddy(JabberId jID, String name, Subscription subscription)
	{
		this.jID = jID;
		this.name = name;
		this.subscription = subscription;
	}

	public CheeonkBuddy(JabberId jID, String name, Subscription subscription, String... groups)
	{
		this.jID = jID;
		this.name = name;
		this.subscription = subscription;
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

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public Subscription getSubscription()
	{
		return subscription;
	}

	@Override
	public void setSubscription(Subscription subscription)
	{
		this.subscription = subscription;
	}

}
