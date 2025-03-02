package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.util.MyModConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.LiquidMemories;

@SpirePatch(
        clz = AbstractPotion.class,
        method = "getPotency",
        paramtypez = { }
)
public class LiquidMemoriesNerfPatch {
    public static int Postfix(int __result, AbstractPotion __instance) {
        if (
                AbstractDungeon.player != null &&
                AbstractDungeon.player.hasRelic("SacredBark") &&
                __instance instanceof LiquidMemories &&
                MyModConfig.getBool("liquidMemoriesNerfed", false)
        ) {
            __result /= 2;
        }

        return __result;
    }
}

