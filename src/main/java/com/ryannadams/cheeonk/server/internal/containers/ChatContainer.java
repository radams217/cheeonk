package com.ryannadams.cheeonk.server.internal.containers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import com.ryannadams.cheeonk.server.internal.wrappers.ChatWrapper;
import com.ryannadams.cheeonk.server.internal.wrappers.MessageWrapper;
import com.ryannadams.cheeonk.shared.chat.CheeonkChat;
import com.ryannadams.cheeonk.shared.message.CheeonkMessage;

public class ChatContainer implements ChatManagerListener, MessageListener
{
	private final Map<ChatWrapper, List<MessageWrapper>> chatMap;
	private final Logger logger;

	public ChatContainer()
	{
		chatMap = new HashMap<ChatWrapper, List<MessageWrapper>>();
		logger = Logger.getLogger("");
	}

	public synchronized ArrayList<CheeonkChat> getChats()
	{
		List<CheeonkChat> newChatList = new ArrayList<CheeonkChat>();

		for (ChatWrapper chat : chatMap.keySet())
		{
			if (!chat.isTransmitted())
			{
				newChatList.add(chat.getClientChat());
				logger.log(Level.FINEST, "Chat " + chat.getThreadID() + " with " + chat.getParticipant() + " to be transmitted.");
				chat.setTransmitted(true);
			}
		}

		return new ArrayList<CheeonkChat>(newChatList);
	}

	@Override
	public void chatCreated(Chat key, boolean createdLocally)
	{
		ChatWrapper chat = new ChatWrapper(key);
		chat.setTransmitted(createdLocally);

		logger.log(Level.FINEST, "Chat Created " + chat.getThreadID() + " with " + chat.getParticipant());

		if (!createdLocally)
		{
			key.addMessageListener(this);
		}

		chatMap.put(chat, new LinkedList<MessageWrapper>());
	}

	public synchronized void sendMessage(CheeonkChat chat, CheeonkMessage message)
	{
		// Store local message sent to message list
		for (ChatWrapper chatWrapper : chatMap.keySet())
		{
			if (chat.getThreadID().equals(chat.getThreadID()))
			{
				try
				{
					chatWrapper.sendMessage(message.getBody());
					logger.log(Level.FINEST, "Sending Message in Chat " + chat.getThreadID() + " to " + chat.getParticipant());
				}
				catch (XMPPException e)
				{
					e.printStackTrace();
				}

				break;
			}
		}

	}

	public synchronized ArrayList<CheeonkMessage> getMessages(CheeonkChat key)
	{
		List<CheeonkMessage> newMessageList = new ArrayList<CheeonkMessage>();

		for (Iterator<MessageWrapper> it = chatMap.get(key).iterator(); it.hasNext();)
		{
			newMessageList.add(it.next().getClientMessage());
			logger.log(Level.FINEST, "Getting Message on Chat " + key.getThreadID() + " to " + key.getParticipant());
			it.remove();
		}

		return new ArrayList<CheeonkMessage>(newMessageList);
	}

	@Override
	public void processMessage(Chat key, Message message)
	{
		switch (message.getType())
		{
			case chat:
				chatMap.get(new ChatWrapper(key)).add(new MessageWrapper(message));
				logger.log(Level.FINEST, "Adding Message from " + message.getFrom() + " to " + message.getTo() + " cheeonking \"" + message.getBody() + "\"");
				break;

			case groupchat:
				break;

			case normal:
				break;

			case headline:
				break;

			case error:
				logger.log(Level.SEVERE, "Error sending Message from " + message.getFrom() + " to " + message.getTo() + " cheeonking \"" + message.getBody()
						+ "\"");
				break;
		}

	}
}
