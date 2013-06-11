package de.wpsmarthome.control;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.wpsmarthome.control.Objects.Light;

// creates single messages and composed messages (multiple messages in one)
public class Messages {
    private static final String sServer = "172.16.0.200";
    private static final int sPort = 12349;
    
    private static final String sLightTopic = "LP.LIGHTCONTROL";
    
    private static final Map<String, String> sNoValues = new HashMap<String, String>();
    
	private static Message message(String action, Map<String, String> values, String topicName) {
		return new SingleMessage(action, values, sServer, sPort, topicName);
	}
	
	private static Message composedMessage(Message... messages) {
		return new ComposedMessage(messages);
	}
	
	public static Message lightSwitchMessage(Objects.Light light, boolean switchItOn) {
		Message message = null;
		
		if (light == Light.KITCHEN_ALL) { // composed message
			Message cookingLightMessage = lightSwitchMessage(Light.KITCHEN_COOKING, switchItOn);
			Message mainLightMessage = lightSwitchMessage(Light.KITCHEN_MAIN, switchItOn);
			message = composedMessage(cookingLightMessage, mainLightMessage);
		} else {
			String actionBaseName = light.toString().toLowerCase(Locale.ENGLISH);
			String actionName = actionBaseName + "_light_" + (switchItOn ? "on" : "off");
			message = message(actionName, sNoValues, sLightTopic);
		}
		
		return message;
	}
}
