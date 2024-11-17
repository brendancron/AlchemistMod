package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.powers.SacredFormPower;
import com.cron.alchemistmod.util.TrackPotions;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.SacredBark;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.Objects;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "hasRelic",
        paramtypez = { String.class }
)
public class SacredFormPatch {
    public static boolean Postfix(boolean __result, AbstractPlayer __instance, String targetID) {
        if (Objects.equals(targetID, SacredBark.ID) && __instance.hasPower(SacredFormPower.POWER_ID) && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            SacredFormPower power = (SacredFormPower) __instance.getPower(SacredFormPower.POWER_ID);
            return TrackPotions.getPotionsUsed() < power.amount;
        } else {
            return __result;
        }
    }
}

