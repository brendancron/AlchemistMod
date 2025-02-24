package com.cron.alchemistmod.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class TrackPotions {
    private static ArrayList<AbstractPotion> potionsUsedThisTurn = new ArrayList<>();
    private static ArrayList<AbstractPotion> potionsUsedThisCombat = new ArrayList<>();
    private static AbstractPotion lastPotionUsed;

    public static final Logger logger = LogManager.getLogger("TrackPotions");

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

    public static int numOfEmptyPotionSlots() {
        if (AbstractDungeon.player != null) {
            int numOfEmptySlots = 0;
            for(AbstractPotion potion: AbstractDungeon.player.potions) {
                if (potion instanceof PotionSlot) {
                    numOfEmptySlots++;
                }
            }

            return numOfEmptySlots;
        } else {
            return 0;
        }
    }
    public static int numOfPotions() {
        if (AbstractDungeon.player != null) {
            int numOfPotions = 0;
            for(AbstractPotion potion: AbstractDungeon.player.potions) {
                if (!(potion instanceof PotionSlot)) {
                    numOfPotions++;
                }
            }

            return numOfPotions;
        } else {
            return 0;
        }
    }

    public static void addPotionSlot(int amount) {
        for (int i = 0; i < amount; i++) {
            AbstractDungeon.player.potionSlots += 1;
            AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));
        }
    }

    public static void removePotionSlot(int amount) {
        // remove from list
        ArrayList<AbstractPotion> potions = AbstractDungeon.player.potions;
        int numPotionsRemoved = 0;
        for (int i = potions.size() - 1; i >= 0; i--) {
            if (potions.get(i) instanceof PotionSlot && numPotionsRemoved < amount) {
                potions.remove(i);
                numPotionsRemoved++;
            }
        }

        while (numPotionsRemoved < amount) {
            potions.remove(potions.size() - 1);
            numPotionsRemoved++;
        }

        // finish details

        AbstractDungeon.player.potionSlots -= amount;

        for (int i = 0; i < potions.size(); i++) {
            potions.get(i).setAsObtained(i);
        }
    }
}
