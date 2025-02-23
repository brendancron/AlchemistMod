package com.cron.alchemistmod.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CheckBossCombat {
    public static boolean isBossCombat() {
        // double check that this is correct
        if (!AbstractDungeon.getCurrRoom().combatEvent) {
            return false;
        }

        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (monster.type == AbstractMonster.EnemyType.BOSS) {
                return true;
            }
        }

        return false;
    }

}
