package com.cron.alchemistmod.powers;

import com.cron.alchemistmod.AlchemistMod;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PotionPotencyPower extends AbstractAlchemistPower {
    public static final String POWER_ID = AlchemistMod.makeID(PotionPotencyPower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String[] DESCRIPTIONS = POWER_STRINGS.DESCRIPTIONS;

    public PotionPotencyPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = POWER_STRINGS.NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        configureTexture(PotionPotencyPower.class);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public int modifyPotionPotency(int basePotency) {
        return basePotency + this.amount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new PotionPotencyPower(this.owner, this.source, this.amount);
    }

    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new PotionPotencyPower(this.owner, this.source, amount);
    }
}
