package com.cron.alchemistmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ChrysopoeiaAction extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final int mult;
    private final AbstractPlayer player;
    private final int energyOnUse;

    public ChrysopoeiaAction(AbstractPlayer player, int mult, boolean freeToPlayOnce, int energyOnUse) {
        this.player = player;
        this.mult = mult;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    @Override
    public void update() {
        int energy = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            energy = this.energyOnUse;
        }

        if (this.player.hasRelic("Chemical X")) {
            energy += 2;
            this.player.getRelic("Chemical X").flash();
        }

        int effect  = AbstractDungeon.player.currentBlock / mult * energy;

        if (effect > 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new RemoveAllBlockAction(player, player)
            );
            AbstractDungeon.actionManager.addToBottom(
                    new GainGoldAction(effect)
            );

            if (!this.freeToPlayOnce) {
                this.player.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
