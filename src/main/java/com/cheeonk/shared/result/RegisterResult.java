package com.cheeonk.shared.result;

import net.customware.gwt.dispatch.shared.Result;

public class RegisterResult implements Result
{
	private boolean isRegistered;

	@Deprecated
	public RegisterResult()
	{

	}

	public RegisterResult(boolean isRegister)
	{
		this.isRegistered = isRegister;
	}

	public boolean isRegistered()
	{
		return isRegistered;
	}

}
