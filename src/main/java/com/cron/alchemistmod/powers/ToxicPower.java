package com.cron.alchemistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class ToxicPower extends AbstractAlchemistPower {

    public static final String POWER_ID = AlchemistMod.makeID(ToxicPower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final Texture tex84 = TextureLoader.getTexture(AlchemistMod.makePowerPath(ToxicPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(AlchemistMod.makePowerPath(ToxicPower.class.getSimpleName() + "32.png"));

    public ToxicPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = POWER_STRINGS.NAME;
        this.ID = POWER_ID;

        this.type = PowerType.DEBUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        updateDescription();
    }

    public void atStartOfTurn() {
        if (this.amount > 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(
                            this.owner,        // The player (target of the Poison)
                            this.source,       // The enemy that applied Toxic (source of the Poison)
                            new PoisonPower(this.owner, this.source, this.amount),
                            this.amount        // The amount of Poison to apply
                    )
            );
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new ToxicPower(this.owner, this.source, this.amount);
    }
    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new ToxicPower(this.owner, this.source, amount);
    }

}
