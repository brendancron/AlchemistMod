package com.cron.alchemistmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChooseAndTransformRandomCardAction extends AbstractGameAction {

    private static final Logger logger = LogManager.getLogger(ChooseAndTransformRandomCardAction.class.getName());
    private int numOfCards;
    private final boolean wholeHand;

    public ChooseAndTransformRandomCardAction(int numOfCards, boolean wholeHand) {
        this.numOfCards = numOfCards;
        this.wholeHand = wholeHand;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        logger.info("ChooseAndTransformRandomCardAction initialized.");
    }
    public ChooseAndTransformRandomCardAction() {
        this(1, false);
    }

    public ChooseAndTransformRandomCardAction(int numOfCards) {
        this(numOfCards, false);
    }

    public ChooseAndTransformRandomCardAction(boolean wholeHand) {
        this(-1, wholeHand);
    }

    @Override
    public void update() {
        logger.info("Update called. Action isDone: " + this.isDone);
        logger.info("wereCardsRetrieved: " + AbstractDungeon.handCardSelectScreen.wereCardsRetrieved);
        logger.info("Selected cards size: " + AbstractDungeon.handCardSelectScreen.selectedCards.size());

        // Do this only on first update
        if (this.duration == this.startDuration) {
            // Check if the player's hand is empty; end the action if true
            if (AbstractDungeon.player.hand.isEmpty()) {
                logger.info("Player's hand is empty. Ending action.");
                this.isDone = true;
                return;
            } else if (wholeHand) {
                numOfCards = AbstractDungeon.player.hand.size();
            }

            // Open selection screen
            logger.info("Opening selection screen.");
            AbstractDungeon.handCardSelectScreen.open("Transform", numOfCards, false, true, true, false, true);
            this.tickDuration();
            return;
        }

        // After the player selects a card
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard selectedCard : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                logger.info("Card selected: " + selectedCard.name);

                // Generate a random replacement card
                AbstractCard randomReplacement = AbstractDungeon.getCard(AbstractDungeon.rollRarity()).makeCopy();
                logger.info("Generated replacement card: " + randomReplacement.name);

                // Add the transformed card to the player's hand with a visual effect
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(randomReplacement));
            }

            // End action after transforming cards
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            this.isDone = true;
            return;
        }

        this.tickDuration();
    }
}
