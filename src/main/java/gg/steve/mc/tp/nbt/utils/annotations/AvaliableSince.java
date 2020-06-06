package gg.steve.mc.tp.nbt.utils.annotations;

import gg.steve.mc.tp.nbt.utils.MinecraftVersion;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({METHOD})
public @interface AvaliableSince {

    MinecraftVersion version();

}