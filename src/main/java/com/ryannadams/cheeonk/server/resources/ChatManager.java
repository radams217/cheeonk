package com.ryannadams.cheeonk.server.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class ChatManager implements ChatManagerListener
{
    public static ChatManager instance;

    public static synchronized ChatManager getInstance()
    {
        if (instance == null)
        {
            instance = new ChatManager();
        }

        return instance;
    }

    private final List<Chat> chatList;
    protected final Map<String, String> messageQueue;

    public ChatManager()
    {
        chatList = new ArrayList<Chat>();
        messageQueue = new ConcurrentHashMap<String, String>();
    }

    
    
    
    
    @Override
    public void chatCreated(Chat chat, boolean createdLocally)
    {
        if (!createdLocally)
        {
            chat.addMessageListener(new MessageListener()
            {
                @Override
                public void processMessage(Chat chat, Message message)
                {
                    messageQueue.put(chat.getParticipant(), message.getBody());
                }
            });

            chatList.add(chat);
        }

    }
}
