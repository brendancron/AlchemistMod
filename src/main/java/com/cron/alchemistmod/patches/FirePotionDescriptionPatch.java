package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.cards.util.DamageCalculationCard;
import com.cron.alchemistmod.powers.PotionPotencyPower;
import com.cron.alchemistmod.util.CheckCombat;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.FirePotion;

@SpirePatch(
        clz = FirePotion.class,
        method = "initializeData",
        paramtypez = { }
)
public class FirePotionDescriptionPatch {
    public static void Postfix(FirePotion __instance) {
        if (!CheckCombat.isCombat()) {
            return;
        }

        AbstractPlayer player = AbstractDungeon.player;
        int potency = __instance.getPotency();

        if (player.hasPower(PotionPotencyPower.POWER_ID)) {
            PotionPotencyPower power = (PotionPotencyPower) player.getPower(PotionPotencyPower.POWER_ID);
            potency = power.modifyPotionPotency(potency);
        }

        PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(__instance.ID);
        __instance.description = potionStrings.DESCRIPTIONS[0]
                + new DamageCalculationCard().getLowestMultidamage(potency)
                + potionStrings.DESCRIPTIONS[1];

        __instance.tips.clear();
        __instance.tips.add(new PowerTip(__instance.name, __instance.description));
    }
}

