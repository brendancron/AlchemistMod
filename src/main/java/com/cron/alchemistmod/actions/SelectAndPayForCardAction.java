package com.cron.alchemistmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.function.Consumer;

public class SelectAndPayForCardAction extends AbstractGameAction {

    private final AbstractPlayer player;

    private CardGroup originalHand = null;

    private final Consumer<AbstractCard> callback;

    public SelectAndPayForCardAction(AbstractPlayer player, Consumer<AbstractCard> callback) {
        this.player = player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.5F;
        this.callback = callback;
    }

    @Override
    public void update() {
        if (this.duration == 0.5F) {

            CardGroup validCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            int currentEnergy = EnergyPanel.totalCount;

            for (AbstractCard c : player.hand.group) {
                if (c.costForTurn <= currentEnergy || c.cost == -1) {
                    validCards.addToTop(c);
                }
            }

            if (validCards.isEmpty()) {
                this.isDone = true;
                return;
            }

            this.originalHand = player.hand;
            player.hand = validCards;

            AbstractDungeon.handCardSelectScreen.open(
                    "Choose a card to echo (Cost must be paid)", // message
                    1,                                           // numCardsToSelect
                    false,                                       // canSelectZero (must select 1)
                    false,                                       // forTransform
                    false,                                       // forUpgrade
                    false,                                       // forPurge
                    false                                        // canCancel
            );

            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractCard chosen = null;
            if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                chosen = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0);

                int energySpent = chosen.costForTurn;
                if (chosen.cost == -1) {
                    energySpent = EnergyPanel.totalCount;
                }

                if (energySpent > 0) {
                    player.loseEnergy(energySpent);
                }
            }

            // 2. Restore the original hand (Crucial step!)
            if (this.originalHand != null) {
                player.hand = this.originalHand;
            }

            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;

            if (chosen != null) {
                this.callback.accept(chosen);
            }

            this.isDone = true;
            return;
        }

        tickDuration();
    }
}