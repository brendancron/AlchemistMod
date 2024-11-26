package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.powers.AbstractAlchemistPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;

@SpirePatch(
        clz = ShowCardAndAddToDiscardEffect.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = { AbstractCard.class }
)
public class CreationPatchDiscard {
    public static void Postfix(ShowCardAndAddToDiscardEffect __instance, AbstractCard srcCard) {
        for (AbstractPower power: AbstractDungeon.player.powers) {
            if (power instanceof AbstractAlchemistPower) {
                ((AbstractAlchemistPower) power).onCreateCard(srcCard);
            }
        }
    }
}

