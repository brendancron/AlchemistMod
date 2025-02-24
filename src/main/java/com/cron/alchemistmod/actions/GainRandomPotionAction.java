package com.cron.alchemistmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GainRandomPotionAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(GainRandomPotionAction.class.getSimpleName());
    public int amount;

    public GainRandomPotionAction(int amount) {
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    public GainRandomPotionAction() {
        this(1);
    }

    @Override
    public void update() {
        if (amount > 0) {
            AbstractPotion potion = AbstractDungeon.returnRandomPotion();
            if (AbstractDungeon.player.hasRelic("Sozu")) {
                AbstractDungeon.player.getRelic("Sozu").flash();
            } else {
                AbstractDungeon.player.obtainPotion(potion);
            }
            amount--;
        } else {
            this.isDone = true;
        }

        this.tickDuration();
    }
}
