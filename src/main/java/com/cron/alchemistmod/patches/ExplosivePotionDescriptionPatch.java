package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.cards.util.DamageCalculationCard;
import com.cron.alchemistmod.relics.PotionLauncher;
import com.cron.alchemistmod.util.CheckCombat;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.ExplosivePotion;

@SpirePatch(
        clz = ExplosivePotion.class,
        method = "initializeData",
        paramtypez = { }
)
public class ExplosivePotionDescriptionPatch {
    public static void Postfix(ExplosivePotion __instance) {
        if (CheckCombat.isCombat() && AbstractDungeon.player.hasRelic(PotionLauncher.ID)) {
            PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString((__instance.ID));

            __instance.description = potionStrings.DESCRIPTIONS[0] + new DamageCalculationCard().getLowestMultidamage(__instance.getPotency()) + potionStrings.DESCRIPTIONS[1];
            __instance.tips.clear();
            __instance.tips.add(new PowerTip(__instance.name, __instance.description));
        }
    }
}

