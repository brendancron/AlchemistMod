package com.cron.alchemistmod.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import java.util.ArrayList;

public class TrackPotions {
    private static ArrayList<AbstractPotion> potionsUsedThisTurn = new ArrayList<>();
    private static ArrayList<AbstractPotion> potionsUsedThisCombat = new ArrayList<>();
    private static AbstractPotion lastPotionUsed;

    public static void atStartOfTurn() {
        potionsUsedThisTurn = new ArrayList<>();
        updatePotions();
    }

    public static void atCombatEnd() {
        potionsUsedThisTurn = new ArrayList<>();
        potionsUsedThisCombat = new ArrayList<>();
        updatePotions();
    }

    public static int getNumPotionsUsedThisTurn() {
        return potionsUsedThisTurn.size();
    }

    public static AbstractPotion getLastPotionUsed() {
        if (potionsUsedThisCombat.size() > 0) {
            return potionsUsedThisCombat.get(potionsUsedThisCombat.size() - 1);
        } else {
            return null;
        }
    }

    public static void updatePotions() {
        for (AbstractPotion p : AbstractDungeon.player.potions) {
            p.initializeData();
        }
    }

    public static void onPotionUsed(AbstractPotion abstractPotion) {
        potionsUsedThisTurn.add(abstractPotion);
        potionsUsedThisCombat.add(abstractPotion);

        updatePotions();
    }
}
