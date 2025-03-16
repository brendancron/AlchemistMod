package com.cron.alchemistmod.actions;

import com.cron.alchemistmod.cards.colorless.GoldNugget;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ChrysopoeiaAction extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final AbstractPlayer player;
    private final int energyOnUse;

    public ChrysopoeiaAction(AbstractPlayer player, boolean freeToPlayOnce, int energyOnUse) {
        this.player = player;
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

        if (energy > 0) {
            addToBot(
                new MakeTempCardInDrawPileAction(new GoldNugget(), energy, true, false)
            );
            if (!this.freeToPlayOnce) {
                this.player.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
