package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.powers.PotionPotencyPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.FirePotion;

@SpirePatch(
        clz = FirePotion.class,
        method = "use",
        paramtypez = { AbstractCreature.class }
)
public class FirePotionDamagePatch {
    public static void Replace(FirePotion __instance, AbstractCreature target) {
        AbstractCreature player = AbstractDungeon.player;
        int potency = __instance.getPotency();
        if (player.hasPower(PotionPotencyPower.POWER_ID)) {
            PotionPotencyPower power = (PotionPotencyPower) player.getPower(PotionPotencyPower.POWER_ID);
            potency = power.modifyPotionPotency(potency);
        }
        DamageInfo info = new DamageInfo(player, potency, DamageInfo.DamageType.THORNS);
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(target, info, AbstractGameAction.AttackEffect.FIRE)
        );
    }
}

