package com.cron.alchemistmod.actions;

import com.cron.alchemistmod.util.TrackPotions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

public class UpdatePotionsAction extends AbstractGameAction {

    public UpdatePotionsAction() {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        TrackPotions.updatePotions();
        this.isDone = true;
    }
}
