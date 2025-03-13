package com.cron.alchemistmod.util.Serialization;

import com.google.gson.*;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.lang.reflect.Type;

public class CardDeserializer implements JsonDeserializer<AbstractCard> {
    @Override
    public AbstractCard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String className = obj.get("className").getAsString();
        try {
            Class<?> clazz = Class.forName(className);
            return context.deserialize(json, clazz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Unknown class: " + className, e);
        }
    }
}

