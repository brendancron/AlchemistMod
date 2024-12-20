package com.cron.alchemistmod.patches;

import com.badlogic.gdx.Gdx;
import com.cron.alchemistmod.powers.AntifactPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;

import java.lang.reflect.Field;
import java.util.Objects;

@SpirePatch(
        clz = ApplyPowerAction.class,
        method = "update",
        paramtypez = { }
)
public class AntifactUpdatePatch {
    public static void Prefix(ApplyPowerAction __instance) {
        try {
            Field powerToApplyField = ApplyPowerAction.class.getDeclaredField("powerToApply");
            powerToApplyField.setAccessible(true);
            AbstractPower powerToApply = (AbstractPower) powerToApplyField.get(__instance);

            Field durationField = AbstractGameAction.class.getDeclaredField("duration");
            durationField.setAccessible(true);
            Float duration = (Float) durationField.get(__instance);

            Field startingDurationField = ApplyPowerAction.class.getDeclaredField("startingDuration");
            startingDurationField.setAccessible(true);
            Float startingDuration = (Float) startingDurationField.get(__instance);
        
            if (__instance.target != null && !__instance.target.isDeadOrEscaped()) {
                if (Objects.equals(duration, startingDuration)) {
                    if (__instance.target.hasPower(AntifactPower.POWER_ID) &&
                            powerToApply.type == AbstractPower.PowerType.BUFF &&
                            !(powerToApply instanceof EndTurnDeathPower)
                    ) {
                        AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(__instance.target, ApplyPowerAction.TEXT[0]));
                        durationField.set(__instance, duration - Gdx.graphics.getDeltaTime());
                        CardCrawlGame.sound.play("NULLIFY_SFX");
                        __instance.target.getPower(AntifactPower.POWER_ID).flashWithoutSound();
                        __instance.target.getPower(AntifactPower.POWER_ID).onSpecificTrigger();
                    }
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

