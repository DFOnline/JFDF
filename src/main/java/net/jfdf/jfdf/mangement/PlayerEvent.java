package net.jfdf.jfdf.mangement;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import net.jfdf.jfdf.blocks.PlayerEventBlock;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PlayerEvent {
    public PlayerEventBlock.Event eventType();
}