package com.ryannadams.cheeonk.shared.result;

import net.customware.gwt.dispatch.shared.Result;

public class SignoutResult implements Result
{
	private boolean isSignedout;

	@Deprecated
	public SignoutResult()
	{

	}

	public SignoutResult(boolean isSignedout)
	{
		this.isSignedout = isSignedout;
	}

	public boolean isSignedout()
	{
		return isSignedout;
	}

}
