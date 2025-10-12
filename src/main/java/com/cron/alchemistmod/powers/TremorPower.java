package com.cron.alchemistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TremorPower extends AbstractAlchemistPower {
    public static final String POWER_ID = AlchemistMod.makeID(TremorPower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture tex84 = TextureLoader.getTexture(AlchemistMod.makePowerPath(PackedEarthPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(AlchemistMod.makePowerPath(PackedEarthPower.class.getSimpleName() + "32.png"));

    public TremorPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = POWER_STRINGS.NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = POWER_STRINGS.DESCRIPTIONS[0] + this.amount + POWER_STRINGS.DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (owner instanceof AbstractPlayer) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAllEnemiesAction((AbstractPlayer)owner, this.amount, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE)
            );
        }

        if (this.amount <= 1) {
            AbstractDungeon.actionManager.addToBottom(
                new RemoveSpecificPowerAction(this.owner, this.owner, this.ID)
            );
        } else {
            this.amount--;
            updateDescription();
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new TremorPower(this.owner, this.source, this.amount);
    }

    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new TremorPower(this.owner, this.source, amount);
    }
}
