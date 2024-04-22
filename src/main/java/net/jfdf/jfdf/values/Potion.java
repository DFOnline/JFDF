package net.jfdf.jfdf.values;

public class Potion implements IPotion {
    public String potionType;
    public int duration = 100;
    public int amplifier = 1;

    public Potion(String potionType) {
        this.potionType = potionType;
    }

    public Potion setDuration(int ticks) {
        this.duration = ticks;

        return this;
    }

    public Potion setAmplifier(int amplifier) {
        this.amplifier = amplifier;

        return this;
    }

    @Override
    public String toString() {
        return "Potion{" +
                "potionType='" + potionType + '\'' +
                ", duration=" + duration +
                ", amplifier=" + amplifier +
                '}';
    }

    @Override
    public String asJSON() {
        return "{\"id\":\"pot\",\"data\":{\"pot\":\"" + potionType
                + "\",\"dur\":" + duration
                + ",\"amp\":" + amplifier + "}}";
    }
}