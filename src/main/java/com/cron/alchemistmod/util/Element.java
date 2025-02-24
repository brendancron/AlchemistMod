package com.cron.alchemistmod.util;

import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.cron.alchemistmod.cards.colorless.*;
import com.cron.alchemistmod.powers.*;

import java.util.Objects;

public enum Element {
    AIR (AirElement.POWER_ID, new AirElementCard()),
    DARK (DarkElement.POWER_ID, new DarkElementCard()),
    EARTH (EarthElement.POWER_ID, new EarthElementCard()),
    FIRE (FireElement.POWER_ID, new FireElementCard()),
    LIGHT (LightElement.POWER_ID, new LightElementCard()),
    MAGIC (MagicElement.POWER_ID, new MagicElementCard()),
    WATER (WaterElement.POWER_ID, new WaterElementCard());

    private final String PowerID;
    private final AbstractAlchemistCard card;
    Element(String PowerID, AbstractAlchemistCard card) {
        this.PowerID = PowerID;
        this.card = card;
    }

    public String getPowerID() {
        return this.PowerID;
    }

    public static Element getElement(String powerID) {
        for(Element element: Element.values()) {
            if (element.isElement(powerID)) {
                return element;
            }
        }

        return null;
    }

    public AbstractAlchemistCard getCard() {
        return (AbstractAlchemistCard) this.card.makeCopy();
    }

    public boolean isElement(String powerID) {
        return Objects.equals(powerID, this.PowerID);
    }
}
