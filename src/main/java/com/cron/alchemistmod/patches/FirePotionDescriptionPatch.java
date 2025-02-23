package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.cards.util.DamageCalculationCard;
import com.cron.alchemistmod.relics.PotionLauncher;
import com.cron.alchemistmod.util.CheckCombat;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.FirePotion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpirePatch(
        clz = FirePotion.class,
        method = "initializeData",
        paramtypez = { }
)
public class FirePotionDescriptionPatch {
    public static void Postfix(FirePotion __instance) {
        Logger logger = LogManager.getLogger(FirePotionDescriptionPatch.class.getSimpleName());

        logger.info("update fire potion");
        if (CheckCombat.isCombat() && AbstractDungeon.player.hasRelic(PotionLauncher.ID)) {
            PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(__instance.ID);

            __instance.description = potionStrings.DESCRIPTIONS[0] + new DamageCalculationCard().getLowestMultidamage(__instance.getPotency()) + potionStrings.DESCRIPTIONS[1];
            __instance.tips.clear();
            __instance.tips.add(new PowerTip(__instance.name, __instance.description));
        }
    }
}

