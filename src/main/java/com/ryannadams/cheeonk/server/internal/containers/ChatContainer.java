package com.ryannadams.cheeonk.server.internal.containers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public ChatContainer()
	{
		chatMap = new HashMap<ChatWrapper, List<MessageWrapper>>();
	}

	public CheeonkChat[] getIncomingChats()
	{
		List<CheeonkChat> newChatList = new ArrayList<CheeonkChat>();

		for (ChatWrapper chat : chatMap.keySet())
		{
			if (!chat.isTransmitted())
			{
				System.out.println("DEBUG: Chat with " + chat.getThreadID() + " found.");
				newChatList.add(chat.getClientChat());
				chat.setTransmitted(true);
			}
		}

		return newChatList.toArray(new CheeonkChat[newChatList.size()]);
	}

	@Override
	public void chatCreated(Chat key, boolean createdLocally)
	{
		ChatWrapper chat = new ChatWrapper(key);
		chat.setTransmitted(createdLocally);

		System.out.println("DEBUG: Chat Created ThreadID: " + chat.getThreadID());

		if (!createdLocally)
		{
			key.addMessageListener(this);
		}

		chatMap.put(chat, new ArrayList<MessageWrapper>());
	}

	public void sendMessage(CheeonkChat chat, CheeonkMessage message)
	{
		// Store local message sent to message list?
		for (ChatWrapper chatWrapper : chatMap.keySet())
		{
			if (chat.getThreadID().equals(chat.getThreadID()))
			{
				try
				{
					chatWrapper.sendMessage(message.getBody());
				}
				catch (XMPPException e)
				{
					e.printStackTrace();
				}

				break;
			}
		}

	}

	public CheeonkMessage[] getMessages(CheeonkChat key)
	{
		List<CheeonkMessage> newMessageList = new ArrayList<CheeonkMessage>();

		for (MessageWrapper message : chatMap.get(key))
		{
			System.out.println("DEBUG: Message from " + message.getFrom() + " received. [" + message.getBody() + "]");
			newMessageList.add(message.getClientMessage());
		}

		// TODO: I'm worried this may cause messages not transmitted to be
		// deleted.
		// This should only clear out sent messages. Low Latency on the server
		// may cause problems.
		chatMap.get(key).clear();

		return newMessageList.toArray(new CheeonkMessage[newMessageList.size()]);
	}

	@Override
	public void processMessage(Chat key, Message message)
	{
		switch (message.getType())
		{
			case chat:
				System.out.println("DEBUG: Adding Message from " + message.getFrom() + " to " + message.getTo() + " to the Queue. [" + message.getBody() + "]");
				chatMap.get(new ChatWrapper(key)).add(new MessageWrapper(message));
				break;

			case groupchat:
				break;

			case normal:
				break;

			case headline:
				break;

			case error:
				System.err.println("ERROR: Message to " + message.getTo() + " from " + message.getFrom() + " wasn't sent. " + message.getError());
				break;
		}

	}
}
