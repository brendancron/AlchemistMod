package com.cron.alchemistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
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

    public TremorPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = POWER_STRINGS.NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        configureTexture(TremorPower.class);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = POWER_STRINGS.DESCRIPTIONS[0] + this.amount + POWER_STRINGS.DESCRIPTIONS[1];
    }

    @Override
    public void onGainElement(AbstractElement element) {
        if (element instanceof EarthElement) {
            if (this.owner instanceof AbstractPlayer) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAllEnemiesAction(AbstractDungeon.player,
                                DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS,
                                AbstractGameAction.AttackEffect.SMASH));
            }
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
