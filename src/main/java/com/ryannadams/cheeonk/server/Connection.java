package com.ryannadams.cheeonk.server;

import java.util.Collection;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import com.google.gwt.event.shared.GwtEvent;
import com.ryannadams.cheeonk.shared.ConnectionKey;
import com.ryannadams.cheeonk.shared.buddy.CheeonkBuddy;
import com.ryannadams.cheeonk.shared.buddy.CheeonkPresence;
import com.ryannadams.cheeonk.shared.buddy.IBuddy;
import com.ryannadams.cheeonk.shared.buddy.IBuddy.Subscription;
import com.ryannadams.cheeonk.shared.buddy.JabberId;
import com.ryannadams.cheeonk.shared.event.AddBuddyEvent;
import com.ryannadams.cheeonk.shared.event.MessageReceivedEvent;
import com.ryannadams.cheeonk.shared.event.PresenceChangeEvent;
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

	public void processRoster()
	{
		for (RosterEntry entry : getRoster().getEntries())
		{
			IBuddy buddy = new CheeonkBuddy(new JabberId(entry.getUser()), entry.getName());

			// TODO: entry.getStatus()

			switch (entry.getType())
			{
				case none:
					buddy.setSubscription(Subscription.NONE);
					break;
				case both:
					buddy.setSubscription(Subscription.BOTH);
					break;
				case to:
					buddy.setSubscription(Subscription.TO);
					break;
				case from:
					buddy.setSubscription(Subscription.FROM);
					break;
				case remove:
					buddy.setSubscription(Subscription.REMOVE);
					break;
			}

			for (RosterGroup group : entry.getGroups())
			{
				group.getName();

			}

			eventDeque.add(new AddBuddyEvent(buddy));
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
		eventDeque.add(new PresenceChangeEvent(Connection.getPresence(presence)));
	}

	public static CheeonkPresence getPresence(Presence presence)
	{
		CheeonkPresence cheeonkPresence = new CheeonkPresence(new JabberId(presence.getFrom()));

		switch (presence.getType())
		{
			case available:
				cheeonkPresence.setType(CheeonkPresence.Type.AVAILABLE);
				break;
			case unavailable:
				cheeonkPresence.setType(CheeonkPresence.Type.UNAVAILABLE);
				break;
		}

		cheeonkPresence.setStatus(presence.getStatus());

		switch (presence.getMode())
		{
			case available:
				cheeonkPresence.setMode(CheeonkPresence.Mode.AVAILABLE);
				break;
			case away:
				cheeonkPresence.setMode(CheeonkPresence.Mode.AWAY);
				break;
			case chat:
				cheeonkPresence.setMode(CheeonkPresence.Mode.CHAT);
				break;
			case dnd:
				cheeonkPresence.setMode(CheeonkPresence.Mode.DND);
				break;
			case xa:
				cheeonkPresence.setMode(CheeonkPresence.Mode.XA);
				break;
		}

		return cheeonkPresence;
	}

	public static Presence getPresence(CheeonkPresence cheeonkPresence)
	{
		Presence presence = new Presence(Presence.Type.available);

		switch (cheeonkPresence.getType())
		{
			case AVAILABLE:
				presence = new Presence(Presence.Type.available);
				break;
			case UNAVAILABLE:
				presence = new Presence(Presence.Type.unavailable);
				break;
		}

		cheeonkPresence.setStatus(presence.getStatus());

		switch (cheeonkPresence.getMode())
		{
			case AVAILABLE:
				presence.setMode(Presence.Mode.available);
				break;
			case AWAY:
				presence.setMode(Presence.Mode.away);
				break;
			case CHAT:
				presence.setMode(Presence.Mode.chat);
				break;
			case DND:
				presence.setMode(Presence.Mode.dnd);
				break;
			case XA:
				presence.setMode(Presence.Mode.xa);
				break;
		}

		return presence;
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

	@Override
	public void chatCreated(Chat chat, boolean isLocal)
	{
		chat.addMessageListener(this);
	}
}
