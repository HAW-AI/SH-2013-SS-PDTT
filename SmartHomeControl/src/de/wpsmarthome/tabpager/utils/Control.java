package de.wpsmarthome.tabpager.utils;

public enum Control {
	LIGHT("Licht"), HEATING("Heizung"), WINDOW("Fenster"), BLINDS("Rollos"), CURTAIN(
			"Gardinen");

	private String name;

	private Control(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
