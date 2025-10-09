package com.cron.alchemistmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PlayStoredCardAction extends AbstractGameAction {
    private final AbstractCard cardToPlay;

    public PlayStoredCardAction(AbstractCard card) {
        this.cardToPlay = card.makeStatEquivalentCopy();
    }

    @Override
    public void update() {
        if (cardToPlay != null) {
            AbstractCard card = cardToPlay.makeStatEquivalentCopy();

            AbstractPlayer p = AbstractDungeon.player;

            card.freeToPlayOnce = true;
            card.purgeOnUse = true;
            card.current_x = Settings.WIDTH / 2f;
            card.current_y = Settings.HEIGHT / 2f;
            card.target_x = Settings.WIDTH / 2f;
            card.target_y = Settings.HEIGHT / 2f;

            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(card, AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng), 0, true, true), false);
        }
        this.isDone = true;
    }
}

