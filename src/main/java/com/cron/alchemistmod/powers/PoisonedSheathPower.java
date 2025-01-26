package com.cron.alchemistmod.powers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.cards.alchemist.PoisonShiv;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PoisonedSheathPower extends AbstractAlchemistPower {
    private final boolean upgraded;
    public static final String POWER_ID = AlchemistMod.makeID(PoisonedSheathPower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final Texture tex84 = TextureLoader.getTexture(AlchemistMod.makePowerPath(PoisonedSheathPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(AlchemistMod.makePowerPath(PoisonedSheathPower.class.getSimpleName() + "32.png"));

    public PoisonedSheathPower(final AbstractCreature owner, final AbstractCreature source, final int amount, boolean upgraded) {
        name = POWER_STRINGS.NAME;
        ID = POWER_ID + upgraded;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.upgraded = upgraded;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        PoisonShiv shiv = new PoisonShiv();
        if (upgraded) {
            shiv.upgrade();
        }
        AbstractDungeon.actionManager.addToBottom(
                new MakeTempCardInHandAction(shiv, this.amount, false)
        );
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            description = POWER_STRINGS.DESCRIPTIONS[0] + amount + POWER_STRINGS.DESCRIPTIONS[1];
        } else {
            description = POWER_STRINGS.DESCRIPTIONS[0] + amount + POWER_STRINGS.DESCRIPTIONS[2];
        }

        if (this.upgraded) {
            description = description + POWER_STRINGS.DESCRIPTIONS[4];
        } else {
            description = description + POWER_STRINGS.DESCRIPTIONS[3];
        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new PoisonedSheathPower(this.owner, this.source, this.amount, this.upgraded);
    }

    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new PoisonedSheathPower(this.owner, this.source, amount, this.upgraded);
    }
}
