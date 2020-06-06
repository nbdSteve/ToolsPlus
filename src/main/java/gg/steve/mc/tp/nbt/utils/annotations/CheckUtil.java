package gg.steve.mc.tp.nbt.utils.annotations;

import gg.steve.mc.tp.nbt.NbtApiException;
import gg.steve.mc.tp.nbt.utils.MinecraftVersion;

import java.lang.reflect.Method;

public class CheckUtil {

    public static boolean isAvaliable(Method method) {
        if (MinecraftVersion.getVersion().getVersionId() < method.getAnnotation(AvaliableSince.class).version().getVersionId())
            throw new NbtApiException("The Method '" + method.getName() + "' is only avaliable for the Versions " + method.getAnnotation(AvaliableSince.class).version() + "+, but still got called!");
        return true;
    }
}