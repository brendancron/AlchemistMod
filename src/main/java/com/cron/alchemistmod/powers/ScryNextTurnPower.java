package com.cron.alchemistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ScryNextTurnPower extends AbstractAlchemistPower {
    public static final String POWER_ID = AlchemistMod.makeID(ScryNextTurnPower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public ScryNextTurnPower(AbstractCreature owner, AbstractCreature source, int amount) {
        this.name = POWER_STRINGS.NAME;
        this.ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        this.amount = amount;

        configureTexture(ScryNextTurnPower.class);

        this.isTurnBased = true;

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new ScryAction(this.amount));
        AbstractDungeon.actionManager.addToBottom(
                new RemoveSpecificPowerAction(this.owner, this.owner, this.ID)
        );
    }

    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new ScryNextTurnPower(owner, source, amount);
    }

    @Override
    public AbstractPower makeCopy() {
        return new ScryNextTurnPower(owner, source, amount);
    }
}
