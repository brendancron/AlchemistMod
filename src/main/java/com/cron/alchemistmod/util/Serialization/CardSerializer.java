package com.cron.alchemistmod.util.Serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.lang.reflect.Type;

public class CardSerializer implements JsonSerializer<AbstractCard> {
    @Override
    public JsonElement serialize(AbstractCard card, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = context.serialize(card).getAsJsonObject();
        obj.addProperty("className", card.getClass().getName()); // Store class name
        return obj;
    }
}