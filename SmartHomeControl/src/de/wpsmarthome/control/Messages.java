package de.wpsmarthome.control;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.graphics.Color;

import de.wpsmarthome.control.Objects.Blind;
import de.wpsmarthome.control.Objects.Curtain;
import de.wpsmarthome.control.Objects.Light;
import de.wpsmarthome.control.Objects.Window;

// creates single messages and composed messages (multiple messages in one)
public class Messages {
    private static final String sServer = "172.16.0.200";
    private static final int sPort = 12349;
    
    private static final String sLightControlTopic = "LP.LIGHTCONTROL";
    private static final String sWindowControlTopic = "WINDOW.CONTROL";
    
    private static final Map<String, String> sNoValues = new HashMap<String, String>();
    
	private static Message lightControlMessage(String action, Map<String, String> values) {
		return new LightControlMessage(action, values, sServer, sPort, sLightControlTopic);
	}
	
	private static Message windowControlMessage(String windowId, int targetWidth) {
		return new WindowControlMessage(windowId, targetWidth, sServer, sPort, sWindowControlTopic);
	}
	
	private static Message composedMessage(Message... messages) {
		return new ComposedMessage(messages);
	}
	
	private static interface MessageResolver<T> {
		Message messageForObject(T object);
	}
	
	private static <T> Message composedMessageFromObjects(T[] objects, MessageResolver<T> messageResolver) {
		Message[] messages = new Message[objects.length];
		for (int i = 0; i < objects.length; ++i) {
			messages[i] = messageResolver.messageForObject(objects[i]);
		}
		return composedMessage(messages);
	}

	public static Message lightSwitchMessage(Light light, final boolean switchItOn) {
		Message message = null;
		
		if (light == Light.ALL) {
			message = composedMessageFromObjects(Light.allObjects(), new MessageResolver<Light>() {
				@Override
				public Message messageForObject(Light object) {
					return lightSwitchMessage(object, switchItOn);
				}
			});
		} else if (light == Light.KITCHEN_ALL) { // composed message
			Message cookingLightMessage = lightSwitchMessage(Light.KITCHEN_COOKING, switchItOn);
			Message mainLightMessage = lightSwitchMessage(Light.KITCHEN_MAIN, switchItOn);
			message = composedMessage(cookingLightMessage, mainLightMessage);
		} else {
			String actionBaseName = light.toString().toLowerCase(Locale.ENGLISH);
			String actionName = actionBaseName + "_light_" + (switchItOn ? "on" : "off");
			message = lightControlMessage(actionName, sNoValues);
		}
		
		return message;
	}
	
	// intensity \elem [0, 100]
	public static Message lightIntensityMessage(Objects.Light light, int intensityArgument) {
		if (intensityArgument < 0) {
			intensityArgument = 0;
		} else if (intensityArgument > 100) {
			intensityArgument = 100;
		}
		
		final int intensity = intensityArgument;
		
		Message message = null;
		
		if (light == Light.ALL) {
			message = composedMessageFromObjects(Light.allObjects(), new MessageResolver<Light>() {
				@Override
				public Message messageForObject(Light object) {
					return lightIntensityMessage(object, intensity);
				}
			});
		} else if (light == Light.KITCHEN_ALL) { // composed message
			Message cookingLightMessage = lightColorMessage(Light.KITCHEN_COOKING, intensity);
			Message mainLightMessage = lightColorMessage(Light.KITCHEN_MAIN, intensity);
			message = composedMessage(cookingLightMessage, mainLightMessage);
		} else {
			String actionBaseName = light.toString().toLowerCase(Locale.ENGLISH);
			String actionName = actionBaseName + "_light_on"; 
			
			Map<String, String> values = new HashMap<String, String>();
			int absoluteIntensity = (int) Math.ceil(255.0/100 * intensity); // convert from [0, 100] to [0, 255]
			values.put("intensity", absoluteIntensity + "");
			
			message = lightControlMessage(actionName, values);
		}
		
		return message;
	}
	
	public static Message lightColorMessage(Objects.Light light, final int color) {
		Message message = null;
		
		if (light == Light.ALL) {
			message = composedMessageFromObjects(Light.allObjects(), new MessageResolver<Light>() {
				@Override
				public Message messageForObject(Light object) {
					return lightColorMessage(object, color);
				}
			});
		} else if (light == Light.KITCHEN_ALL) { // composed message
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
			
			message = lightControlMessage(actionName, values);
		}
		
		return message;
	}

	// height: 0 => open ; 1 => half open ; 2 => closed
	public static Message blindHeightMessage(final Blind blind, final int heightParameter) {
		final int height = (heightParameter < 0 || heightParameter > 2) ? 0 : heightParameter;
		Message message;
		
		if (blind == Blind.ALL) {
			message = composedMessageFromObjects(Blind.allObjects(), new MessageResolver<Blind>() {
				@Override
				public Message messageForObject(Blind object) {
					return blindHeightMessage(object, height);
				}
			});
		} else {
			String actionBaseName;
			if (blind == Blind.DINING || blind == Blind.KITCHEN) {
				actionBaseName = "dining_kitchen";
			} else {
				actionBaseName = blind.toString().toLowerCase(Locale.ENGLISH);
			}
			
			String actionCommand = height == 0 ? "open" : (height == 1 ? "half" : "close"); 
			String actionName = "blinds_" + actionBaseName + "_" + actionCommand;
			message = lightControlMessage(actionName, sNoValues);
		}
		
		return message;
	}
	
	public static Message curtainStateMessage(final Curtain curtain, final boolean openIt) {
		Message message;
		
		if (curtain == Curtain.ALL) {
			message = composedMessageFromObjects(Curtain.allObjects(), new MessageResolver<Curtain>() {
				@Override
				public Message messageForObject(Curtain object) {
					return curtainStateMessage(object, openIt);
				}
			});
		} else {
			String actionBaseName;
			if (curtain == Curtain.CORRIDOR) {
				actionBaseName = "sleeping_hall";
			} else if (curtain == Curtain.SLEEPING) {
				actionBaseName = "sleeping_window";
			} else {
				actionBaseName = curtain.toString().toLowerCase(Locale.ENGLISH);
			}
			
			String actionCommand = openIt ? "open" : "close";
			String actionName = actionBaseName + "_curtain_" + actionCommand;
			message = lightControlMessage(actionName, sNoValues);
		}
		
		return message;
	}

	public static Message windowStateMessage(final Window window, final boolean leaveItAjar) {
		String windowId = window.toString();
		int targetWidth = leaveItAjar ? 20 : 0;
		return windowControlMessage(windowId, targetWidth);
	}
}
