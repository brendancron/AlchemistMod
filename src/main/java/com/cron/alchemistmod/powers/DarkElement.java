package com.cron.alchemistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DarkElement extends AbstractElement {
    public static final String POWER_ID = AlchemistMod.makeID(DarkElement.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final Texture tex84 = TextureLoader.getTexture(AlchemistMod.makePowerPath(DarkElement.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(AlchemistMod.makePowerPath(DarkElement.class.getSimpleName() + "32.png"));

    public DarkElement(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        super(owner, source, amount);

        this.name = POWER_STRINGS.NAME;
        this.ID = POWER_ID;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void testForPotions() {
        // if already have a different element
        if (owner.hasPower(AirElement.POWER_ID)) {
            brewPotion(AirElement.POWER_ID, new GamblersBrew());
        } else if (owner.hasPower(EarthElement.POWER_ID)) {
            brewPotion(EarthElement.POWER_ID, new SpeedPotion());
        } else if (owner.hasPower(FireElement.POWER_ID)) {
            brewPotion(FireElement.POWER_ID, new SteroidPotion());
        } else if (owner.hasPower(LightElement.POWER_ID)) {
            brewPotion(LightElement.POWER_ID, new LiquidBronze());
        } else if (owner.hasPower(MagicElement.POWER_ID)) {
            brewPotion(MagicElement.POWER_ID, new DuplicationPotion());
        } else if (owner.hasPower(WaterElement.POWER_ID)) {
            brewPotion(WaterElement.POWER_ID, new PoisonPotion());
        }

        while (this.amount >= 2) {
            AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(
                    new EnergyPotion()
            ));
            this.amount -= 2;
        }

        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, DarkElement.POWER_ID));
        }
    }

    @Override
    public void updateDescription() {
        this.description = POWER_STRINGS.DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new DarkElement(this.owner, this.source, this.amount);
    }

    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new DarkElement(this.owner, this.source, amount);
    }
}
