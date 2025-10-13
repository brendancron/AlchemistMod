package com.cron.alchemistmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

public class PurgeCardAction extends AbstractGameAction {
    private final AbstractPlayer player;
    private final AbstractCard card;

    private boolean effectStarted = false;

    public PurgeCardAction(AbstractPlayer player, AbstractCard card) {
        this.player = player;
        this.card = card;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    @Override
    public void update() {
        if (!effectStarted) {
            AbstractDungeon.topLevelEffects.add(
                    new PurgeCardEffect(card, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f)
            );
            effectStarted = true;

            player.masterDeck.removeCard(card.cardID);
            player.discardPile.removeCard(card);
            player.drawPile.removeCard(card);
            player.hand.removeCard(card);
            player.exhaustPile.removeCard(card);
        }

        tickDuration();
    }
}
