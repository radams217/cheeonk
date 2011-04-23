package com.ryannadams.cheeonk.server.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import com.ryannadams.cheeonk.client.chat.ClientChat;
import com.ryannadams.cheeonk.client.chat.ClientMessage;

public class ChatContainer implements ChatManagerListener, MessageListener
{
	private final Map<ChatWrapper, List<MessageWrapper>> chatMap;

	public ChatContainer()
	{
		chatMap = new HashMap<ChatWrapper, List<MessageWrapper>>();
	}

	public ClientChat[] getIncomingChats()
	{
		List<ClientChat> newChatList = new ArrayList<ClientChat>();

		for (ChatWrapper chat : chatMap.keySet())
		{
			if (!chat.isTransmitted())
			{
				System.out.println("DEBUG: Chat with " + chat.getThreadID() + " found.");
				newChatList.add(chat.getClientChat());
				chat.setTransmitted(true);
			}
		}

		return newChatList.toArray(new ClientChat[newChatList.size()]);
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

	public void sendMessage(ClientChat chat, String message)
	{
		// Store local message sent to message list?
		for (ChatWrapper chatWrapper : chatMap.keySet())
		{
			if (chat.getThreadID().equals(chat.getThreadID()))
			{
				try
				{
					chatWrapper.getChat().sendMessage(message);
				}
				catch (XMPPException e)
				{
					e.printStackTrace();
				}

				break;
			}
		}

	}

	public ClientMessage[] getMessages(ClientChat key)
	{
		List<ClientMessage> newMessageList = new ArrayList<ClientMessage>();

		// Do in reverse order, break when isTransmitted is true will save
		// looping through the entire array
		for (MessageWrapper message : chatMap.get(key))
		{
			if (!message.isTransmitted())
			{
				System.out.println("DEBUG: Message from " + message.getMessage().getFrom() + " received. [" + message.getMessage().getBody() + "]");
				newMessageList.add(message.getClientMessage());

				message.setTransmitted(true);
			}
		}

		return newMessageList.toArray(new ClientMessage[newMessageList.size()]);
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
