package com.cron.alchemistmod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscardAndReduceAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private static final float DURATION = Settings.ACTION_DUR_FAST;

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscardAndReduceAction.class);

    public DiscardAndReduceAction(AbstractPlayer p) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = p;
    }

    public void update() {
        if (this.duration == DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open("Select a card to discard", 1, false, false);
            AbstractDungeon.player.hand.applyPowers();
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for(AbstractCard discardedCard : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                LOGGER.info("Discarded Card: " + discardedCard.name);
                discardedCard.modifyCostForCombat(-1);
                discardedCard.flash(Color.GOLD.cpy());
                this.p.hand.moveToDiscardPile(discardedCard);
                discardedCard.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }
}
