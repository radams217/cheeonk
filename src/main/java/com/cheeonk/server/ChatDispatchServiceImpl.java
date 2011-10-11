package com.cheeonk.server;

import net.customware.gwt.dispatch.client.standard.StandardDispatchService;
import net.customware.gwt.dispatch.server.DefaultActionHandlerRegistry;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.InstanceActionHandlerRegistry;
import net.customware.gwt.dispatch.server.SimpleDispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;

import com.cheeonk.server.handler.AddBuddyHandler;
import com.cheeonk.server.handler.ChangePresenceHandler;
import com.cheeonk.server.handler.GetEventHandler;
import com.cheeonk.server.handler.RegisterHandler;
import com.cheeonk.server.handler.SendMessageHandler;
import com.cheeonk.server.handler.SigninHandler;
import com.cheeonk.server.handler.SignoutHandler;
import com.cheeonk.server.handler.UpdateBuddyHandler;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ChatDispatchServiceImpl extends RemoteServiceServlet implements StandardDispatchService
{
	private Dispatch dispatch;

	public ChatDispatchServiceImpl()
	{
		InstanceActionHandlerRegistry registry = new DefaultActionHandlerRegistry();
		registry.addHandler(new RegisterHandler());
		registry.addHandler(new SendMessageHandler());
		registry.addHandler(new SigninHandler());
		registry.addHandler(new SignoutHandler());
		registry.addHandler(new GetEventHandler());
		registry.addHandler(new AddBuddyHandler());
		registry.addHandler(new ChangePresenceHandler());
		registry.addHandler(new UpdateBuddyHandler());

		dispatch = new SimpleDispatch(registry);
	}

	@Override
	public Result execute(Action<?> action) throws DispatchException
	{
		try
		{
			return dispatch.execute(action);
		}
		catch (RuntimeException e)
		{
			log("Exception while executing " + action.getClass().getName() + ": " + e.getMessage(), e);
			throw e;
		}
	}

}