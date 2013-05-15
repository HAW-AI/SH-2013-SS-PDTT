package de.wpsmarthome.control;

import java.util.HashMap;
import java.util.Map;

// creates single messages and composed messages (multiple messages in one)
public class Messages {
    private static final String sServer = "172.16.0.200";
    private static final int sPort = 12349;
    
    private static final String sLightTopic = "LP.LIGHTCONTROL";
    
    private static final Map<String, String> sNoValues = new HashMap<String, String>();
    
	private static Message message(String action, Map<String, String> values, String topicName) {
		return new SingleMessage(action, values, sServer, sPort, topicName);
	}
	
	public static Message kitchenLightSwitchMessage(boolean switchItOn) {
        String action = "kitchen_main_light_" + (switchItOn ? "on" : "off");
        return message(action, sNoValues, sLightTopic);
	}
}
