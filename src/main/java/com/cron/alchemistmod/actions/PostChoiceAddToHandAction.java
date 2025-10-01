package com.cron.alchemistmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class PostChoiceAddToHandAction extends AbstractGameAction {
    private boolean cardRetrieved = false;
    private final boolean freeThisTurn;

    public PostChoiceAddToHandAction(boolean freeThisTurn) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.freeThisTurn = freeThisTurn;
    }

    public void update() {
        if (!this.cardRetrieved) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                AbstractCard chosenCard = AbstractDungeon.cardRewardScreen.discoveryCard;

                if (freeThisTurn) {
                    chosenCard.setCostForTurn(0);
                }

                if (AbstractDungeon.player.hand.size() < 10) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(
                            chosenCard,
                            (float)Settings.WIDTH / 2.0F,
                            (float)Settings.HEIGHT / 2.0F
                    ));
                }
                // Otherwise, add to discard pile
                else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(
                            chosenCard,
                            (float)Settings.WIDTH / 2.0F,
                            (float)Settings.HEIGHT / 2.0F
                    ));
                }

                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }

            this.cardRetrieved = true;
        }

        this.isDone = true;
    }
}
