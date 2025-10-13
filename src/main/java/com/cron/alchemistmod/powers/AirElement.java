package com.cron.alchemistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.Element;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AirElement extends AbstractElement {
    public static final String POWER_ID = AlchemistMod.makeID(AirElement.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public AirElement(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        super(owner, source, amount, Element.AIR);

        configureTexture(AirElement.class);

        this.name = POWER_STRINGS.NAME;
        this.ID = POWER_ID;

        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = POWER_STRINGS.DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new AirElement(this.owner, this.source, this.amount);
    }

    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new AirElement(this.owner, this.source, amount);
    }
}
