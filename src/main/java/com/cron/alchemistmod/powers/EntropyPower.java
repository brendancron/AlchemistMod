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

public class EntropyPower extends AbstractAlchemistPower {

    public static final String POWER_ID = AlchemistMod.makeID(EntropyPower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final Texture tex84 = TextureLoader.getTexture(AlchemistMod.makePowerPath(EntropyPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(AlchemistMod.makePowerPath(EntropyPower.class.getSimpleName() + "32.png"));

    public EntropyPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
    public void onGainElement(AbstractElement element) {
        if (!(element instanceof DarkElement)) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(owner, source, new DarkElement(owner, source, this.amount), this.amount)
            );
        }
    }

    @Override
    public void updateDescription() {
        description = POWER_STRINGS.DESCRIPTIONS[0] + amount + POWER_STRINGS.DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new EntropyPower(this.owner, this.source, this.amount);
    }
    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new EntropyPower(this.owner, this.source, amount);
    }
}
