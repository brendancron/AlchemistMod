package com.cron.alchemistmod.actions;

import com.cron.alchemistmod.powers.EchoedCardPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class EchoesOfDarknessAction extends AbstractGameAction {

    private final AbstractCreature source;
    private final int amount;
    private final boolean exhaustCard;

    private CardGroup originalHand = null;

    public EchoesOfDarknessAction(AbstractCreature source, int amount, boolean exhaust) {
        this.source = source;
        this.amount = amount;
        this.exhaustCard = exhaust;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.5F;
    }

    @Override
    public void update() {
        if (this.duration == 0.5F) {

            CardGroup validCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            int currentEnergy = EnergyPanel.totalCount;

            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.costForTurn <= currentEnergy || c.cost == -1) {
                    validCards.addToTop(c);
                }
            }

            if (validCards.isEmpty()) {
                this.isDone = true;
                return;
            }

            this.originalHand = AbstractDungeon.player.hand;
            AbstractDungeon.player.hand = validCards;

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

            if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                AbstractCard chosen = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0);

                int energySpent = chosen.costForTurn;
                if (chosen.cost == -1) {
                    energySpent = EnergyPanel.totalCount;
                }

                if (energySpent > 0) {
                    AbstractDungeon.player.loseEnergy(energySpent);
                }

                AbstractCard echoedCopy = chosen.makeStatEquivalentCopy();
                this.addToBot(
                    new ApplyPowerAction(AbstractDungeon.player, source,
                        new EchoedCardPower(AbstractDungeon.player, source, amount, echoedCopy), amount)
                );

                if (exhaustCard) {
                    this.addToBot(new ExhaustSpecificCardAction(chosen, this.originalHand));
                } else {
                    this.addToBot(new DiscardSpecificCardAction(chosen, this.originalHand));
                }
            }

            // 2. Restore the original hand (Crucial step!)
            if (this.originalHand != null) {
                AbstractDungeon.player.hand = this.originalHand;
            }

            // 3. Clean up and set done
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            this.isDone = true;
            return;
        }

        tickDuration();
    }
}