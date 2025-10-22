package com.cron.alchemistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class NextExhaustBecomesDiscardPower extends AbstractAlchemistPower {
    public static final String POWER_ID = AlchemistMod.makeID(NextExhaustBecomesDiscardPower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public NextExhaustBecomesDiscardPower(final AbstractCreature owner, final AbstractCreature source, int amount) {
        this.name = POWER_STRINGS.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = amount;

        this.updateDescription();
        this.type = PowerType.BUFF;

        configureTexture(NextExhaustBecomesDiscardPower.class);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (this.amount > 0 && action.exhaustCard) {
            action.exhaustCard = false;
            AbstractDungeon.actionManager.addToBottom(
                new DiscardSpecificCardAction(card)
            );
            this.flash();
            this.amount--;
            if (this.amount <= 0) {
                AbstractDungeon.actionManager.addToTop(
                    new RemoveSpecificPowerAction(this.owner, this.owner, this.ID)
                );
            }
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = "The next card you Exhaust is instead placed into your Discard Pile.";
        } else {
            this.description = "The next #b" + this.amount + " cards you Exhaust are instead placed into your Discard Pile.";
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new NextExhaustBecomesDiscardPower(this.owner, this.source, this.amount);
    }

    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new NextExhaustBecomesDiscardPower(this.owner, this.source, amount);
    }
}
