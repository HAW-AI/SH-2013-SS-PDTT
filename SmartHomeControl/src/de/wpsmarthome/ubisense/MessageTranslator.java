package de.wpsmarthome.ubisense;

import de.wpsmarthome.tabpager.utils.Context;

public class MessageTranslator {
	public static final String DINING_ROOM = "diningRoom";
	public static final String KITCHEN = "kitchen";
	public static final String LOUNGE = "lounge";
	public static final String BATH_ROOM = "bathRoom";
	public static final String SLEEPING_ROOM = "sleepingRoom";
	public static final String HALL = "hall";
	
	public static Context roomNameToContext(String room) {
		if (room.equals(DINING_ROOM)) {
			return Context.DINING;
		} else if (room.equals(KITCHEN)) {
			return Context.KITCHEN;
		} else if (room.equals(LOUNGE)) {
			return Context.LOUNGE;
		} else if (room.equals(SLEEPING_ROOM)) {
			return Context.BEDROOM;
		} else if (room.equals(HALL)) {
			return Context.HALL;
		} else {
			return null;
		}
	}
}
