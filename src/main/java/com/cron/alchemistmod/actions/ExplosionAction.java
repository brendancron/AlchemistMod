package com.cron.alchemistmod.actions;

import com.cron.alchemistmod.powers.FireElement;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ExplosionAction extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final boolean upgraded;
    private final AbstractPlayer player;
    private final int energyOnUse;

    public ExplosionAction(AbstractPlayer player, boolean upgraded, boolean freeToPlayOnce, int energyOnUse) {
        this.player = player;
        this.upgraded = upgraded;
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

        if (this.player.hasRelic("Chemical X")) {
            effect += 2;
            this.player.getRelic("Chemical X").flash();
        }

        if (this.upgraded) {
            effect++;
        }

        if (effect > 0) {
            this.addToBot(new ApplyPowerAction(this.player, this.player, new FireElement(this.player, this.player, effect), effect));
            if (!this.freeToPlayOnce) {
                this.player.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
