package com.cron.alchemistmod;

import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.cron.alchemistmod.cards.AlchemistStrike;

@SpireInitializer
public class AlchemistMod implements EditCardsSubscriber, EditStringsSubscriber {

    public static final String MOD_ID = "AlchemistMod";

    public AlchemistMod() {
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        new AlchemistMod();
    }

    @Override
    public void receiveEditStrings() {
        // Load custom card strings
        BaseMod.loadCustomStringsFile(CardStrings.class, "localization/eng/CardStrings.json");
    }

    @Override
    public void receiveEditCards() {
        // Register the custom card
        BaseMod.addCard(new AlchemistStrike());
    }

    public static String makeID(String idText) {
        return MOD_ID + ":" + idText;
    }
}
