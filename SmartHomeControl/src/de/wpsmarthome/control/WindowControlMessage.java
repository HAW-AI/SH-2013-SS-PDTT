package de.wpsmarthome.control;

import java.io.IOException;

import android.os.AsyncTask;
import android.util.Log;

public class WindowControlMessage implements Message {
	
	private final String mWindowId;
	private final int mTargetWidth;
	private final String mServer;
	private final int mPort;
	private final String mTopicName;
	
	WindowControlMessage(String windowId, int targetWidth, String server, int port, String topicName) {
		mWindowId = windowId;
		mTargetWidth = targetWidth;
		mServer = server;
		mPort = port;
		mTopicName = topicName;
	}

	@Override
	public void send() {
		Log.d(getClass().getSimpleName(), "send message " + getJsonMessage());
		
		new AsyncTask<String, Void, Void>() {

			@Override
			protected Void doInBackground(String... _params) {
				try {
		            AndroidPublisher publisher = new AndroidPublisher(mServer,
		                    mPort, mTopicName);
		            publisher.setMessage(getJsonMessage());
		            publisher.publishToTopic();
		        } catch (IOException e) {
		            Log.e(WindowControlMessage.this.getClass().getSimpleName(), "Can't publish the message (" + e + ")");
		        }
				return null;
			}
			
		}.execute();
	}
	
	private String getJsonMessage() {
		return "{ \"" + mWindowId + "\": [ \"" + mTargetWidth + "\", \"FAST\" ] }";
	}
}
