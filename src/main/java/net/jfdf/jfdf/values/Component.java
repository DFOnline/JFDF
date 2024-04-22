package net.jfdf.jfdf.values;

public class Component implements IComponent {

	private String value = "";

	public static Component New() {
		return new Component();
	}

	public Component Set(String value) {
		this.value = value;
		return this;
	}

	public String Get() {
		return value;
	}

	@Override
	public String toString() {
		return "Component{" + "value=\"" + value + "\"}";
	}

	public String asJSON() {
		return "{\"id\":\"comp\",\"data\":{\"name\":\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}}";
	}

	public static Component AsVariable(Variable variable) {
		return new Component().Set("%var(" + variable.getName() + ")");
	}

	public static Component AsListValue(Variable list, Number index) {
		return new Component().Set("%index(" + list.getName() + ", " + index.getValue() + ")");
	}
}
