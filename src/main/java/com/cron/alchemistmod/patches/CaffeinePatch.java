package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.powers.CaffeinePower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static basemod.BaseMod.logger;

@SpirePatch(
        clz = DrawCardAction.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = { AbstractCreature.class, int.class, boolean.class }
)
public class CaffeinePatch {
    public static void Postfix(DrawCardAction __instance, AbstractCreature source, int amount, boolean endTurnDraw) {
        logger.info("try to change the draw");
        if (AbstractDungeon.player.hasPower(CaffeinePower.POWER_ID) && __instance.amount != 0) {
            AbstractPower caffeinePower = AbstractDungeon.player.getPower(CaffeinePower.POWER_ID);
            if (caffeinePower != null) {
                __instance.amount = __instance.amount + caffeinePower.amount;
            }
        }
    }
}

