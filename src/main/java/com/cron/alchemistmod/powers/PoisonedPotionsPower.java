package com.cron.alchemistmod.powers;

/* This does not update potion descriptions when removed */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PoisonedPotionsPower extends AbstractAlchemistPower {
    public static final String POWER_ID = AlchemistMod.makeID(PoisonedPotionsPower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final Texture tex84 = TextureLoader.getTexture(AlchemistMod.makePowerPath(PoisonedPotionsPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(AlchemistMod.makePowerPath(PoisonedPotionsPower.class.getSimpleName() + "32.png"));
    public static final Logger logger = LogManager.getLogger("TheAlchemist");

    public PoisonedPotionsPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = POWER_STRINGS.NAME;
        this.ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onUsePotion(AbstractPotion potion) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(this.owner, this.source, new PoisonPower(this.owner, this.source, this.amount), this.amount)
        );
    }

    @Override
    public void updateDescription() {
        description = POWER_STRINGS.DESCRIPTIONS[0] + amount + POWER_STRINGS.DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PoisonedPotionsPower(this.owner, this.source, this.amount);
    }

    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new PoisonedPotionsPower(this.owner, this.source, amount);
    }
}
