package com.cron.alchemistmod.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class CheckCombat {
    public static boolean isCombat() {
        return AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }
    public static boolean isBossCombat() {
        // double check that this is correct
        if (!isCombat()) {
            return false;
        }

        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (monster.type == AbstractMonster.EnemyType.BOSS) {
                return true;
            }
        }

        return false;
    }
    public static boolean isEliteCombat() {
            if (!isCombat()) {
                return false;
            }

            return AbstractDungeon.getCurrRoom().eliteTrigger;
    }
}
