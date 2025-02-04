package com.cron.alchemistmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class BreezyAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public BreezyAction(int amount) {
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            int numCardsDiscarded = 0;
            for (int i = 0; i < AbstractDungeon.player.hand.group.size() && numCardsDiscarded != this.amount; i++) {
                AbstractDungeon.actionManager.addToBottom(
                        new DiscardSpecificCardAction(AbstractDungeon.player.hand.group.get(i))
                );
                numCardsDiscarded++;
            }

            AbstractDungeon.actionManager.addToBottom(
                    new DrawCardAction(numCardsDiscarded)
            );
        }


        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = uiStrings.TEXT;
    }
}