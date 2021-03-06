package com.cheeonk.client.widgets.chat;

import com.cheeonk.shared.buddy.IBuddy;
import com.cheeonk.shared.message.IMessage;

/**
 * @author radams217
 * 
 *         This defines a chatwidget's container, be it a dialog box, some type
 *         of snap window, etc. Eventually the user will be able to choose their
 *         layout. This will facilitate hiding and showing the chat container
 *         for when a message is received and the container needs to be
 *         displayed or closed if the user closes the chat.
 */
public interface IChatWidget
{
	void minimize();

	void maximize();

	void close();

	boolean isMinimized();

	boolean isMaximized();

	boolean isClosed();

	void addMessage(IMessage message);

	IBuddy getParticipant();
}
