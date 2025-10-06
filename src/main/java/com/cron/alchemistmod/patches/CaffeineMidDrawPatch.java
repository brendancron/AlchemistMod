package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.powers.CaffeinePower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static basemod.BaseMod.logger;

@SpirePatch(
        clz = DrawCardAction.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = { int.class, boolean.class }
)
public class CaffeineMidDrawPatch {
    public static void Postfix(DrawCardAction __instance, int amount, boolean clearDrawHistory) {
        logger.info("revert the draw");
        if (!clearDrawHistory && AbstractDungeon.player.hasPower(CaffeinePower.POWER_ID) && __instance.amount != 0) {
            AbstractPower caffeinePower = AbstractDungeon.player.getPower(CaffeinePower.POWER_ID);
            if (caffeinePower != null) {
                __instance.amount = __instance.amount - caffeinePower.amount;
            }
        }
    }
}

