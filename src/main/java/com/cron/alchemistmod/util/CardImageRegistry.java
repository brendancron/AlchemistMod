package com.cron.alchemistmod.util;

import java.util.HashMap;
import java.util.Map;

public class CardImageRegistry {
    private static final Map<Class<?>, String> IMAGE_CACHE = new HashMap<>();

    public static void addImgSrc(Class<?> clazz, String imgSrc) {
        IMAGE_CACHE.put(clazz, imgSrc);
    }

    public static String tryGet(Class<?> clazz) {
        return IMAGE_CACHE.getOrDefault(clazz, "");
    }
}
