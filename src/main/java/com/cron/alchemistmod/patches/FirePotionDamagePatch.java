package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.cards.util.DamageCalculationCard;
import com.cron.alchemistmod.relics.PotionLauncher;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.FirePotion;

@SpirePatch(
        clz = FirePotion.class,
        method = "use",
        paramtypez = { AbstractCreature.class }
)
public class FirePotionDamagePatch {
    public static void Replace(FirePotion __instance, AbstractCreature target) {
        DamageInfo info;
        if (AbstractDungeon.player.hasRelic(PotionLauncher.ID) && target instanceof AbstractMonster) {
            int damage = new DamageCalculationCard().getDamage(__instance.getPotency(), (AbstractMonster) target);
            info = new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS);
        } else {
            info = new DamageInfo(AbstractDungeon.player, __instance.getPotency(), DamageInfo.DamageType.THORNS);
        }

        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(target, info, AbstractGameAction.AttackEffect.FIRE)
        );
    }
}

