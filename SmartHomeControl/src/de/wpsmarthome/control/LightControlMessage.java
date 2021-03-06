package de.wpsmarthome.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.os.AsyncTask;
import android.util.Log;

public class LightControlMessage implements Message {
	
	private static final String API_ID = "patricktill";
	
	private final String mAction;
	private final Map<String, String> mValues;
	private final String mServer;
	private final int mPort;
	private final String mTopicName;
	
	LightControlMessage(String action, Map<String, String> values, String server, int port, String topicName) {
		mAction = action;
		mValues = new HashMap<String, String>(values);
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
		            Log.e(LightControlMessage.this.getClass().getSimpleName(), "Can't publish the message (" + e + ")");
		        }
				return null;
			}
			
		}.execute();
	}
	
	private String getJsonMessage() {
		String values;
		
		if (mValues.isEmpty()) {
			values = "{}";
		} else {
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			for (Entry<String, String> entry : mValues.entrySet()) {
				builder.append(" \"" + entry.getKey() + "\": \"" + entry.getValue() + "\","); 
			}
			builder.deleteCharAt(builder.length() - 1); // remove last ","
			builder.append(" }");
			
			values = builder.toString();
		}
		
        String message =
                "{" +
            		"\"action\": \"" + mAction + "\"," +
            		"\"values\": " + values + "," +
            		"\"Id\": \"" + API_ID + "\"," +
            		"\"Version\": null" +
        		"}";
        return message;
	}

	@Override
	public String toString() {
		return getJsonMessage();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mAction == null) ? 0 : mAction.hashCode());
		result = prime * result + mPort;
		result = prime * result + ((mServer == null) ? 0 : mServer.hashCode());
		result = prime * result
				+ ((mTopicName == null) ? 0 : mTopicName.hashCode());
		result = prime * result + ((mValues == null) ? 0 : mValues.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LightControlMessage other = (LightControlMessage) obj;
		if (mAction == null) {
			if (other.mAction != null)
				return false;
		} else if (!mAction.equals(other.mAction))
			return false;
		if (mPort != other.mPort)
			return false;
		if (mServer == null) {
			if (other.mServer != null)
				return false;
		} else if (!mServer.equals(other.mServer))
			return false;
		if (mTopicName == null) {
			if (other.mTopicName != null)
				return false;
		} else if (!mTopicName.equals(other.mTopicName))
			return false;
		if (mValues == null) {
			if (other.mValues != null)
				return false;
		} else if (!mValues.equals(other.mValues))
			return false;
		return true;
	}
	
	
}
