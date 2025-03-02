package com.cron.alchemistmod.util;

import com.cron.alchemistmod.AlchemistMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;

import java.util.Properties;

public class MyModConfig {
    private static SpireConfig config;

    public static void load() {
        try {
            Properties defaults = new Properties();

            defaults.put("toyOrnathoperDisabled", "false");
            defaults.put("liquidMemoriesNerfed", "false");

            config = new SpireConfig(AlchemistMod.getModID(), "config", defaults);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getBool(String key, boolean defaultValue) {
        return config != null ? config.getBool(key) : defaultValue;
    }

    public static void setBool(String key, boolean value) {
        if (config != null) {
            config.setBool(key, value);
            save();
        }
    }

    public static void save() {
        try {
            if (config != null) {
                config.save();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

