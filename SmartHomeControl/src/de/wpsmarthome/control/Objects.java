package de.wpsmarthome.control;

public final class Objects {
	public enum Light {
		ALL,
		LOUNGE,
		KITCHEN_COOKING,
		KITCHEN_MAIN,
		KITCHEN_ALL,
		DINING,
		CORRIDOR,
		SLEEPING,
		BATHROOM;
		
		public static Light[] allObjects() {
			return new Light[] { LOUNGE, KITCHEN_COOKING, KITCHEN_MAIN, DINING, CORRIDOR, SLEEPING };
		}
	}
	
	public enum Blind {
		ALL,
		LOUNGE,
		KITCHEN,
		DINING,
		SLEEPING;
		
		public static Blind[] allObjects() {
			return new Blind[] { LOUNGE, KITCHEN, DINING, SLEEPING };
		}
	}
	
	public enum Curtain {
		ALL,
		LOUNGE,
		CORRIDOR,
		SLEEPING;
		
		public static Curtain[] allObjects() {
			return new Curtain[] { LOUNGE, CORRIDOR, SLEEPING };
		}
	}
	
	public enum Window {
		ALL,
		KITCHEN,
		DINING;
		
		public static Window[] allObjects() {
			return new Window[] { KITCHEN, DINING };
		}
	}
}
