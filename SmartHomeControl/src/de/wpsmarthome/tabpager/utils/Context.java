package de.wpsmarthome.tabpager.utils;

import static de.wpsmarthome.tabpager.utils.Control.*;

public enum Context {
	ALL("Alle Räume") {
		@Override
		public Control[] getControls() {
			return new Control[] { LIGHT, CURTAIN, BLINDS, WINDOW, HEATING };
		}
	},
	LOUNGE("Lounge") {
		@Override
		public Control[] getControls() {
			return new Control[] { LIGHT, CURTAIN, BLINDS, HEATING };
		}
	},
	KITCHEN("Küche") {
		@Override
		public Control[] getControls() {
			return new Control[] { LIGHT, BLINDS, WINDOW };
		}
	},
	HALL("Flur") {
		@Override
		public Control[] getControls() {
			return new Control[] { LIGHT, CURTAIN };
		}
	},
	BEDROOM("Schlafbereich") {
		@Override
		public Control[] getControls() {
			return new Control[] { LIGHT, CURTAIN, BLINDS, HEATING };
		}
	},
	DINING("Essbereich") {
		@Override
		public Control[] getControls() {
			return new Control[] { LIGHT, BLINDS, WINDOW, HEATING };
		}
	};

	private String name;

	private Context(String name) {
		this.name = name;
	}

	public abstract Control[] getControls();

	@Override
	public String toString() {
		return name;
	}
}
