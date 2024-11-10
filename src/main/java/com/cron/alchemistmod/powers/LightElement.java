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

public class LightElement extends ElementPower {
    public AbstractCreature source;

    public static final String POWER_ID = AlchemistMod.makeID(LightElement.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(AlchemistMod.makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(AlchemistMod.makePowerPath("placeholder_power32.png"));

    public LightElement(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
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
    public void onInitialApplication() {
        super.onInitialApplication();
        testForPotions();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        testForPotions();
    }

    public void brewPotion(String elementID, AbstractPotion potion) {
        AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(potion));
        AbstractPower element = owner.getPower(elementID);

        element.amount -= 1;
        this.amount -= 1;

        if (element.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, elementID));
        }
    }

    public void testForPotions() {
        // if already have a different element
        if (owner.hasPower(AirElement.POWER_ID)) {
            brewPotion(AirElement.POWER_ID, new SneckoOil());
        } else if (owner.hasPower(DarkElement.POWER_ID)) {
            brewPotion(DarkElement.POWER_ID, new LiquidBronze());
        } else if (owner.hasPower(EarthElement.POWER_ID)) {
            brewPotion(EarthElement.POWER_ID, new DexterityPotion());
        } else if (owner.hasPower(FireElement.POWER_ID)) {
            brewPotion(FireElement.POWER_ID, new StrengthPotion());
        } else if (owner.hasPower(MagicElement.POWER_ID)) {
            brewPotion(MagicElement.POWER_ID, new RegenPotion());
        } else if (owner.hasPower(WaterElement.POWER_ID)) {
            brewPotion(WaterElement.POWER_ID, new PowerPotion());
        }

        while (this.amount >= 2) {
            AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(
                    new BlessingOfTheForge()
            ));
            this.amount -= 2;
        }

        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, LightElement.POWER_ID));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new LightElement(this.owner, this.source, this.amount);
    }

    @Override
    public ElementPower makeCopy(int amount) {
        return new LightElement(this.owner, this.source, amount);
    }
}
