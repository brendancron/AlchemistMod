package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.cron.alchemistmod.powers.AbstractAlchemistPower;
import com.cron.alchemistmod.util.TrackPotions;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.TopPanel;

import java.util.ArrayList;

@SpirePatch(
        clz = TopPanel.class,
        method = "destroyPotion",
        paramtypez = { int.class }
)
public class TriggerOnDestroyPotionPatch {
    private static AbstractPotion destroyedPotion;
    public static void Prefix(TopPanel __instance, int slot) {
        destroyedPotion = AbstractDungeon.player.potions.get(slot);
    }
    public static void Postfix(TopPanel __instance, int slot) {
        boolean potionWasUsed = destroyedPotion == TrackPotions.getLastPotionUsed();

        triggerOnDestroyPotion(AbstractDungeon.player.hand.group, potionWasUsed);
        triggerOnDestroyPotion(AbstractDungeon.player.drawPile.group, potionWasUsed);
        triggerOnDestroyPotion(AbstractDungeon.player.discardPile.group, potionWasUsed);
        triggerOnDestroyPotion(AbstractDungeon.player.exhaustPile.group, potionWasUsed);

        if (potionWasUsed) {
            for (AbstractPower power : AbstractDungeon.player.powers) {
                if (power instanceof AbstractAlchemistPower) {
                    ((AbstractAlchemistPower)power).onUsePotion(destroyedPotion);
                }
            }
        } else {
            for (AbstractPower power : AbstractDungeon.player.powers) {
                if (power instanceof AbstractAlchemistPower) {
                    ((AbstractAlchemistPower)power).onDiscardPotion(destroyedPotion);
                }
            }
        }
    }

    public static void triggerOnDestroyPotion(ArrayList<AbstractCard> group, boolean potionWasUsed) {
        for(AbstractCard card : group) {
            if (card instanceof AbstractAlchemistCard) {
                if (potionWasUsed) {
                    ((AbstractAlchemistCard) card).triggerOnUsePotion(destroyedPotion);
                } else {
                    ((AbstractAlchemistCard) card).triggerOnDiscardPotion(destroyedPotion);
                }
            }
        }
    }
}

