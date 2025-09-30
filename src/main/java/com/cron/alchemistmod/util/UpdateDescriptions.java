package com.cron.alchemistmod.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class UpdateDescriptions {
    public static void updatePotions() {
        for (AbstractPotion potion : AbstractDungeon.player.potions) {
            potion.initializeData();
        }
    }

    public static void updatePowers() {
        for (AbstractPower power : AbstractDungeon.player.powers) {
            power.updateDescription();
        }
    }
}
