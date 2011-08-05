package com.cheeonk.client.i18n;

/**
 * Interface to represent the messages contained in resource bundle:
 * /Users/radams217
 * /playground/cheeonk/src/main/resources/com/ryannadams/cheeonk/
 * client/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages
{

	/**
	 * Translated "Enter your name".
	 * 
	 * @return translated "Enter your name"
	 */
	@DefaultMessage("Enter your name")
	@Key("nameField")
	String nameField();

	/**
	 * Translated "Send".
	 * 
	 * @return translated "Send"
	 */
	@DefaultMessage("Send")
	@Key("sendButton")
	String sendButton();
}
