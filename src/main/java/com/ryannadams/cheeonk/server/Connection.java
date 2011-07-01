package com.ryannadams.cheeonk.server;

import java.util.Collection;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import com.google.gwt.event.shared.GwtEvent;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.JabberId;
import com.ryannadams.cheeonk.shared.event.MessageReceivedEvent;
import com.ryannadams.cheeonk.shared.message.CheeonkMessage;
import com.ryannadams.cheeonk.shared.message.IMessage;

public class Connection extends XMPPConnection implements RosterListener, ChatManagerListener, MessageListener
{
	private final BlockingDeque<GwtEvent> eventDeque;

	public Connection(ConnectionKey key)
	{
		super(new ConnectionConfiguration(key.getHost(), key.getPort()));
		this.eventDeque = new LinkedBlockingDeque<GwtEvent>();
	}

	public BlockingDeque<GwtEvent> getEventDeque()
	{
		return eventDeque;
	}

	public void addListeners()
	{
		getChatManager().addChatListener(this);
		getRoster().addRosterListener(this);
	}

	public void removeListeners()
	{
		getChatManager().removeChatListener(this);
		getRoster().removeRosterListener(this);
	}

	@Override
	public void entriesAdded(Collection<String> entries)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void entriesDeleted(Collection<String> entries)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void entriesUpdated(Collection<String> entries)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void presenceChanged(Presence presence)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void chatCreated(Chat chat, boolean createdLocally)
	{
		chat.addMessageListener(this);
	}

	public void sendMessage(IMessage message)
	{
		try
		{
			getChatManager().createChat(message.getTo().getJabberId(), this).sendMessage(message.getBody());
		}
		catch (XMPPException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void processMessage(Chat chat, Message message)
	{
		switch (message.getType())
		{
			case chat:
				CheeonkMessage cheeonkMessage = new CheeonkMessage(new JabberId(message.getTo()), new JabberId(message.getFrom()), message.getBody());
				eventDeque.add(new MessageReceivedEvent(cheeonkMessage));
				break;

			case groupchat:
				break;

			case normal:
				break;

			case headline:
				break;

			case error:
				break;
		}

	}
}
