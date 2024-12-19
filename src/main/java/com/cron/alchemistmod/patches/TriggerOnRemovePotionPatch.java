package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.cron.alchemistmod.powers.AbstractAlchemistPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "removePotion",
        paramtypez = { AbstractPotion.class }
)
public class TriggerOnRemovePotionPatch {
    private static int slot;

    public static void Prefix(AbstractPlayer __instance, AbstractPotion potionOption) {
        slot = __instance.potions.indexOf(potionOption);
    }

    public static void Postfix(AbstractPlayer __instance, AbstractPotion potionOption) {
        if (slot >= 0) {
            triggerRemovePotion(AbstractDungeon.player.hand.group, potionOption);
            triggerRemovePotion(AbstractDungeon.player.drawPile.group, potionOption);
            triggerRemovePotion(AbstractDungeon.player.discardPile.group, potionOption);
            triggerRemovePotion(AbstractDungeon.player.exhaustPile.group, potionOption);
            for (AbstractPower power : AbstractDungeon.player.powers) {
                if (power instanceof AbstractAlchemistPower) {
                    ((AbstractAlchemistPower)power).onDiscardPotion(potionOption);
                }
            }
        }
    }

    public static void triggerRemovePotion(ArrayList<AbstractCard> group, AbstractPotion potionOption) {
        for(AbstractCard card : group) {
            if (card instanceof AbstractAlchemistCard) {
                ((AbstractAlchemistCard) card).triggerOnDiscardPotion(potionOption);
            }
        }
    }
}

