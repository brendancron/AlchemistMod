package com.cron.alchemistmod.powers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CaffeinePower extends AbstractAlchemistPower {

    public static final String POWER_ID = AlchemistMod.makeID(CaffeinePower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public CaffeinePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = POWER_STRINGS.NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        configureTexture(CaffeinePower.class);

        updateDescription();
    }

    @Override
    public void onDrawOrDiscard() {

    }

    @Override
    public void updateDescription() {
        description = POWER_STRINGS.DESCRIPTIONS[0] + amount + POWER_STRINGS.DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new CaffeinePower(this.owner, this.source, this.amount);
    }
    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new CaffeinePower(this.owner, this.source, amount);
    }
}
