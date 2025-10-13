package com.cron.alchemistmod.powers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.actions.BreezyAction;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BreezyPower extends AbstractAlchemistPower {
    public static final String POWER_ID = AlchemistMod.makeID(BreezyPower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public BreezyPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = POWER_STRINGS.NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        configureTexture(BreezyPower.class);

        updateDescription();
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        AbstractDungeon.actionManager.addToBottom(
                new BreezyAction(this.amount)
        );
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(
                new RemoveSpecificPowerAction(this.owner, this.source, this)
        );
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            description = POWER_STRINGS.DESCRIPTIONS[0];
        } else {
            description = POWER_STRINGS.DESCRIPTIONS[1] + amount + POWER_STRINGS.DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new BreezyPower(owner, source, amount);
    }

    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new BreezyPower(owner, source, amount);
    }
}
