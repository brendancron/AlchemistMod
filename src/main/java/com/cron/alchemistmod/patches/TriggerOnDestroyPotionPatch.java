package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.TopPanel;

import java.util.ArrayList;

@SpirePatch(
        clz = TopPanel.class,
        method = "destroyPotion",
        paramtypez = { int.class }
)
public class TriggerOnDestroyPotionPatch {
    public static void Postfix(TopPanel __instance, int slot) {
        triggerOnDestroyPotion(AbstractDungeon.player.hand.group);
        triggerOnDestroyPotion(AbstractDungeon.player.drawPile.group);
        triggerOnDestroyPotion(AbstractDungeon.player.discardPile.group);
        triggerOnDestroyPotion(AbstractDungeon.player.exhaustPile.group);
    }

    public static void triggerOnDestroyPotion(ArrayList<AbstractCard> group) {
        for(AbstractCard card : group) {
            if (card instanceof AbstractAlchemistCard) {
                ((AbstractAlchemistCard) card).triggerOnDestroyPotion();
            }
        }
    }
}

