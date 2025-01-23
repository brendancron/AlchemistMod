package com.cron.alchemistmod.powers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AntifactPower extends AbstractAlchemistPower {
    public static final String POWER_ID = AlchemistMod.makeID(AntifactPower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final Texture tex84 = TextureLoader.getTexture(AlchemistMod.makePowerPath(AntifactPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(AlchemistMod.makePowerPath(AntifactPower.class.getSimpleName() + "32.png"));

    public AntifactPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = POWER_STRINGS.NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void onSpecificTrigger() {
        if (this.amount <= 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else {
            this.addToTop(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            description = POWER_STRINGS.DESCRIPTIONS[0] + amount + POWER_STRINGS.DESCRIPTIONS[1];
        } else {
            description = POWER_STRINGS.DESCRIPTIONS[0] + amount + POWER_STRINGS.DESCRIPTIONS[2];
        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new AntifactPower(owner, source, amount);
    }

    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new AntifactPower(owner, source, amount);
    }
}
