package com.ryannadams.cheeonk.server;

import java.util.Collection;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import com.google.gwt.event.shared.GwtEvent;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.buddy.CheeonkBuddy;
import com.ryannadams.cheeonk.shared.buddy.IBuddy;
import com.ryannadams.cheeonk.shared.buddy.JabberId;
import com.ryannadams.cheeonk.shared.event.AddBuddyEvent;
import com.ryannadams.cheeonk.shared.event.MessageReceivedEvent;
import com.ryannadams.cheeonk.shared.event.PresenceChangeEvent;
import com.ryannadams.cheeonk.shared.message.CheeonkMessage;
import com.ryannadams.cheeonk.shared.message.IMessage;

public class Connection extends XMPPConnection implements RosterListener, MessageListener
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
		getRoster().addRosterListener(this);
	}

	public void removeListeners()
	{
		getRoster().removeRosterListener(this);
	}

	public void processRoster()
	{
		for (RosterEntry entry : getRoster().getEntries())
		{
			eventDeque.add(new AddBuddyEvent(new CheeonkBuddy(new JabberId(entry.getUser()), entry.getName())));
		}
	}

	@Override
	public void entriesAdded(Collection<String> entries)
	{

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
		IBuddy buddy = new CheeonkBuddy();

		eventDeque.add(new PresenceChangeEvent(buddy));
	}

	public void sendMessage(IMessage message)
	{
		try
		{
			getChatManager().createChat(message.getTo().toString(), this).sendMessage(message.getBody());
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
