package com.ryannadams.cheeonk.shared.chat;

/**
 * @author radams217
 * 
 *         This defines the contract for an object to be considered a chat
 *         object in this framework.
 */
public interface IChat
{
	/**
	 * @return the unique thread id from the server
	 */
	String getThreadID();

	/**
	 * @return the other user involved in the chat
	 */
	String getParticipant();
}
