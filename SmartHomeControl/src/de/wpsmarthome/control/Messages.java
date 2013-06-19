package de.wpsmarthome.control;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.graphics.Color;
import android.util.Log;

import de.wpsmarthome.control.Objects.Light;

// creates single messages and composed messages (multiple messages in one)
public class Messages {
    private static final String sServer = "172.16.0.200";
    private static final int sPort = 12349;
    
    private static final String sLightTopic = "LP.LIGHTCONTROL";
    private static final String sBlindsTopic = "LP.LIGHTCONTROL";
    
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
	
	// intensity \elem [0, 100]
	public static Message lightIntensityMessage(Objects.Light light, int intensity) {
		if (intensity < 0) {
			intensity = 0;
		} else if (intensity > 100) {
			intensity = 100;
		}
		
		Message message = null;
		
		if (light == Light.KITCHEN_ALL) { // composed message
			Message cookingLightMessage = lightColorMessage(Light.KITCHEN_COOKING, intensity);
			Message mainLightMessage = lightColorMessage(Light.KITCHEN_MAIN, intensity);
			message = composedMessage(cookingLightMessage, mainLightMessage);
		} else {
			String actionBaseName = light.toString().toLowerCase(Locale.ENGLISH);
			String actionName = actionBaseName + "_light_on"; 
			
			Map<String, String> values = new HashMap<String, String>();
			int absoluteIntensity = (int) Math.ceil(255.0/100 * intensity); // convert from [0, 100] to [0, 255]
			values.put("intensity", absoluteIntensity + "");
			
			message = message(actionName, values, sLightTopic);
		}
		
		return message;
	}
	
	public static Message lightColorMessage(Objects.Light light, int color) {
		Message message = null;
		
		if (light == Light.KITCHEN_ALL) { // composed message
			Message cookingLightMessage = lightColorMessage(Light.KITCHEN_COOKING, color);
			Message mainLightMessage = lightColorMessage(Light.KITCHEN_MAIN, color);
			message = composedMessage(cookingLightMessage, mainLightMessage);
		} else {
			String actionBaseName = light.toString().toLowerCase(Locale.ENGLISH);
			String actionName = actionBaseName + "_light_color";
			
			Map<String, String> values = new HashMap<String, String>();
			values.put("red", Color.red(color) + "");
			values.put("green", Color.green(color) + "");
			values.put("blue", Color.blue(color) + "");
			
			message = message(actionName, values, sLightTopic);
		}
		
		return message;
	}

	// height: 0 => open ; 1 => half open ; 2 => closed
	public static Message blindHeightMessage(int height) {
		if (height < 0 || height > 2) {
			height = 0;
		}
		
//		String action = isChecked ? "blinds_open" : "blinds_close";
//		action = "blinds_close"; // DOESNT WORK (maybe, but only for sleeping hall)
//		action = "blinds_open"; // WORKS
//		action = "BlindsClose";
//		action = "BlindsOpen";
//		action = "blinds_dining_kitchen_close"; // WORKS
//		action = "blinds_lounge_close"; // DOESNT WORK
//		return message(action, sNoValues, sBlindsTopic);
		return new Message() {
			@Override
			public void send() {
				Log.d(getClass().getSimpleName(), "BlindsDummyMessage");
			}
		};
	}
}
