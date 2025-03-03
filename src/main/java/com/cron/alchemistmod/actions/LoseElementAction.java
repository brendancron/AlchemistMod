package com.cron.alchemistmod.actions;

import com.cron.alchemistmod.powers.AbstractElement;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LoseElementAction extends AbstractGameAction {
    public LoseElementAction() {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof AbstractElement) {
                AbstractDungeon.actionManager.addToTop(
                        new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, power)
                );
                break;
            }
        }

        this.isDone = true;
    }
}
