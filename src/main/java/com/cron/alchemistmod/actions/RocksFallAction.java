package com.cron.alchemistmod.actions;

import com.cron.alchemistmod.powers.EarthElement;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class RocksFallAction extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final int damage;
    private final int energyOnUse;

    public RocksFallAction(int damage, boolean freeToPlayOnce, int energyOnUse) {
        this.damage = damage;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (AbstractDungeon.player.hasRelic("Chemical X")) {
            effect += 2;
            AbstractDungeon.player.getRelic("Chemical X").flash();
        }

        if (effect > 0) {

            for (int i = 0 ; i < effect; i++) {
                AbstractDungeon.actionManager.addToBottom(
                        new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY)
                );
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EarthElement(AbstractDungeon.player, AbstractDungeon.player, 1), 1)
                );
            }

            if (!this.freeToPlayOnce) {
                AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}

