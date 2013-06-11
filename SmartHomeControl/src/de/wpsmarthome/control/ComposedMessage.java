package de.wpsmarthome.control;

public class ComposedMessage implements Message {
	Message[] mMessages;
	
	public ComposedMessage(Message... messages) {
		mMessages = messages;
	}

	@Override
	public void send() {
		for (Message m : mMessages) {
			m.send();
		}
	}

}
