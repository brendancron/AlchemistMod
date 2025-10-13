package com.cron.alchemistmod.util;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;

public class PotionUtils {
    public static boolean hasFreeSlot(AbstractPlayer player) {
        if (player == null || player.potions == null) {
            return false;
        }

        for (AbstractPotion potion : player.potions) {
            if (potion instanceof PotionSlot) {
                return true;
            }
        }
        return false;
    }
}
