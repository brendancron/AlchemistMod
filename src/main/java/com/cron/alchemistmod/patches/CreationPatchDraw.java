package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.powers.AbstractAlchemistPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

@SpirePatch(
        clz = ShowCardAndAddToDrawPileEffect.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = { AbstractCard.class, boolean.class, boolean.class }
)
public class CreationPatchDraw {
    public static void Postfix(ShowCardAndAddToDrawPileEffect __instance, AbstractCard srcCard, boolean cardOffset, boolean toBottom) {
        for (AbstractPower power: AbstractDungeon.player.powers) {
            if (power instanceof AbstractAlchemistPower) {
                ((AbstractAlchemistPower) power).onCreateCard(srcCard);
            }
        }
    }
}

