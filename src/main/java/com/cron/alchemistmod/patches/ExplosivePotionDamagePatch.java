package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.relics.PotionLauncher;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.ExplosivePotion;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

@SpirePatch(
        clz = ExplosivePotion.class,
        method = "use",
        paramtypez = { AbstractCreature.class }
)
public class ExplosivePotionDamagePatch {
    public static void Replace(ExplosivePotion __instance, AbstractCreature target) {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped()) {
                AbstractDungeon.actionManager.addToBottom(
                        new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.1F)
                );
            }
        }

        AbstractDungeon.actionManager.addToBottom(
                new WaitAction(0.5F)
        );
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(__instance.getPotency(),
                        !AbstractDungeon.player.hasRelic(PotionLauncher.ID)
                ), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE)
        );
    }
}

