package com.cron.alchemistmod.util.Serialization;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class SkipFieldExclusionStrategy implements ExclusionStrategy {
    private final String fieldNameToSkip;

    public SkipFieldExclusionStrategy(String fieldNameToSkip) {
        this.fieldNameToSkip = fieldNameToSkip;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getName().equals(fieldNameToSkip); // Skip conflicting field
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false; // Never skip entire classes
    }
}
