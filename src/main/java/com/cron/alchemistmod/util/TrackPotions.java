package com.cron.alchemistmod.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class TrackPotions {
    private static int potionsUsed;

    public static void atStartOfTurn() {
        potionsUsed = 0;
        updatePotions();
    }

    public static int getPotionsUsed() {
        return potionsUsed;
    }

    public static int increasePotionsUsed() {
        return increasePotionsUsed(1);
    }

    public static int increasePotionsUsed(int value) {
        potionsUsed += value;
        updatePotions();
        return potionsUsed;
    }

    public static void updatePotions() {
        for (AbstractPotion p : AbstractDungeon.player.potions) {
            p.initializeData();
        }
    }

    public static void onPotionUsed() {
        increasePotionsUsed();
    }
}
