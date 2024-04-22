package net.jfdf.jfdf.mangement;

import net.jfdf.jfdf.blocks.EntityEventBlock;
import net.jfdf.jfdf.blocks.PlayerEventBlock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityEvent {
    public EntityEventBlock.Event eventType();
}