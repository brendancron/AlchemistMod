package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.powers.AbstractAlchemistPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

@SpirePatch(
        clz = ShowCardAndAddToHandEffect.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = { AbstractCard.class }
)
public class CreationPatchHand {
    public static void Postfix(ShowCardAndAddToHandEffect __instance, AbstractCard card) {
        for (AbstractPower power: AbstractDungeon.player.powers) {
            if (power instanceof AbstractAlchemistPower) {
                ((AbstractAlchemistPower) power).onCreateCard(card);
            }
        }
    }
}

