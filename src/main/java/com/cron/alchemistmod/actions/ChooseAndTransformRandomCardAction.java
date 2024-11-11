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

    public ChooseAndTransformRandomCardAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = false;
        logger.info("ChooseAndTransformRandomCardAction initialized.");
    }

    @Override
    public void update() {
        logger.info("Update called. Action isDone: " + this.isDone);
        logger.info("wereCardsRetrieved: " + AbstractDungeon.handCardSelectScreen.wereCardsRetrieved);
        logger.info("Selected cards size: " + AbstractDungeon.handCardSelectScreen.selectedCards.size());

        // Check if the player's hand is empty; end the action if true
        if (AbstractDungeon.player.hand.isEmpty()) {
            logger.info("Player's hand is empty. Ending action.");
            this.isDone = true;
            return;
        }

        // If selection retrieval hasn't occurred and no card is selected, open selection screen
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved && AbstractDungeon.handCardSelectScreen.selectedCards.isEmpty()) {
            logger.info("Opening selection screen.");
            AbstractDungeon.handCardSelectScreen.open("Choose a card to transform", 1, false, false);
            this.tickDuration();
            return;
        }

        // After the player selects a card
        if (!AbstractDungeon.handCardSelectScreen.selectedCards.isEmpty()) {
            AbstractCard selectedCard = AbstractDungeon.handCardSelectScreen.selectedCards.getBottomCard();
            logger.info("Card selected: " + selectedCard.name);

            // Generate a random replacement card
            AbstractCard randomReplacement = AbstractDungeon.getCard(AbstractDungeon.rollRarity());
            logger.info("Generated replacement card: " + randomReplacement.name);

            // Add the transformed card to the player's hand with a visual effect
            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(randomReplacement));

            // Clear selected cards and reset selection state for future uses
            AbstractDungeon.handCardSelectScreen.selectedCards.clear();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = false;

            // Mark the action as complete
            logger.info("Transformation complete. Setting isDone to true.");
            this.isDone = true;
            return;
        }

        logger.info("End of update method without any action taken.");
    }
}
