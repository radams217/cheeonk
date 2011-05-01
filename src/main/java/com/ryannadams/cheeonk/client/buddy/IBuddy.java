package com.ryannadams.cheeonk.client.buddy;

public interface IBuddy
{
	String getJID();

	String getName();

	boolean isAvailable();

	boolean isAway();
}
