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
        method = "obtainPotion",
        paramtypez = { AbstractPotion.class }
)
public class TriggerOnObtainPotionPatch {
    public static boolean Postfix(boolean __result, AbstractPlayer __instance, AbstractPotion potionToObtain) {
        if (__result) {
            triggerOnObtainPotion(AbstractDungeon.player.hand.group, potionToObtain);
            triggerOnObtainPotion(AbstractDungeon.player.drawPile.group, potionToObtain);
            triggerOnObtainPotion(AbstractDungeon.player.discardPile.group, potionToObtain);
            triggerOnObtainPotion(AbstractDungeon.player.exhaustPile.group, potionToObtain);
            for (AbstractPower power : AbstractDungeon.player.powers) {
                if (power instanceof AbstractAlchemistPower) {
                    ((AbstractAlchemistPower)power).onObtainPotion(potionToObtain);
                }
            }
        }

        return __result;
    }

    public static void triggerOnObtainPotion(ArrayList<AbstractCard> group, AbstractPotion potionToObtain) {
        for(AbstractCard card : group) {
            if (card instanceof AbstractAlchemistCard) {
                ((AbstractAlchemistCard) card).triggerOnObtainPotion(potionToObtain);
            }
        }
    }
}

