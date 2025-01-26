package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.cards.PoisonShiv;
import com.cron.alchemistmod.util.CustomTags;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AccuracyPower;

import java.util.ArrayList;

@SpirePatch(
        clz = AccuracyPower.class,
        method = "updateExistingShivs",
        paramtypez = { }
)
public class ShivPatch {
    public static void Postfix(AccuracyPower __instance) {
        updateShivs(AbstractDungeon.player.hand.group, __instance);
        updateShivs(AbstractDungeon.player.drawPile.group, __instance);
        updateShivs(AbstractDungeon.player.discardPile.group, __instance);
        updateShivs(AbstractDungeon.player.exhaustPile.group, __instance);
    }

    public static void updateShivs(ArrayList<AbstractCard> cards, AccuracyPower __instance) {
        for (AbstractCard card : cards) {
            if (card.hasTag(CustomTags.SHIV)) {
                if (!card.upgraded) {
                    card.baseDamage = 4 + __instance.amount;
                } else {
                    card.baseDamage = 6 + __instance.amount;
                }
                if (card instanceof PoisonShiv) {
                    if (!card.upgraded) {
                        card.magicNumber = card.baseMagicNumber = PoisonShiv.MAGIC + __instance.amount;
                    } else {
                        card.magicNumber = card.baseMagicNumber = PoisonShiv.MAGIC + PoisonShiv.MAGIC_UPGRADE + __instance.amount;
                    }
                }
            }
        }
    }
}

