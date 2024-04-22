package net.jfdf.jfdf.values;

public class Sound implements ISound {
	public String name;
	public String variant = null;
	public float pitch = 1.0f;
	public float volume = 2.0f;
	
	public Sound(String name) {
		this.name = name;
	}

	public Sound(String name, String variant) {
		this.name = name;
		this.variant = variant;
	}
	
	public Sound setPitch(float pitch) {
		this.pitch = pitch;
		return this;
	}
	
	public Sound setVolume(float volume) {
		this.volume = volume;
		return this;
	}

	@Override
	public String toString() {
		return "Sound{" +
				"name='" + name + '\'' +
				", variant='" + (variant == null ? "random" : variant) + '\'' +
				", pitch=" + pitch +
				", volume=" + volume +
				'}';
	}

	public String asJSON() {
		return "{\"id\":\"snd\",\"data\":{\"sound\":\"" + name + "\",\"pitch\":" + pitch + (variant != null ? ",\"variant\":\"" + variant + "\"" : "") + ",\"vol\":" + volume +"}}";
	}

}
