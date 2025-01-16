package com.cron.alchemistmod.powers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class LeechStrengthPower extends AbstractAlchemistPower {
    public AbstractCreature source;
    public int previousAmount;

    public static final String POWER_ID = AlchemistMod.makeID(LeechStrengthPower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final Texture tex84 = TextureLoader.getTexture(AlchemistMod.makePowerPath(LeechStrengthPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(AlchemistMod.makePowerPath(LeechStrengthPower.class.getSimpleName() + "32.png"));

    public LeechStrengthPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = POWER_STRINGS.NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            previousAmount = AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
        } else {
            previousAmount = 0;
        }

        updateDescription();
    }

    @Override
    public void onAnyPowerApplied(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power instanceof StrengthPower && target == AbstractDungeon.player && power.amount > 0) {
            int strengthLost = this.amount * power.amount;
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(this.owner ,this.source , new StrengthPower(this.owner, -strengthLost), -strengthLost)
            );
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(
                new RemoveSpecificPowerAction(this.owner, this.owner, this.ID)
        );
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = POWER_STRINGS.DESCRIPTIONS[0];
        } else if (amount > 1) {
            description = POWER_STRINGS.DESCRIPTIONS[1] + amount + POWER_STRINGS.DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new LeechStrengthPower(this.owner, this.source, this.amount);
    }
    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new LeechStrengthPower(this.owner, this.source, amount);
    }
}
