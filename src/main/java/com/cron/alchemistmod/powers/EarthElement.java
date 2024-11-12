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

public class EarthElement extends AbstractElement {
    public static final String POWER_ID = AlchemistMod.makeID(EarthElement.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final Texture tex84 = TextureLoader.getTexture(AlchemistMod.makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(AlchemistMod.makePowerPath("placeholder_power32.png"));

    public EarthElement(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        super(owner, source, amount);

        this.name = powerStrings.NAME;
        this.ID = POWER_ID;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void testForPotions() {
        // if already have a different element
        if (owner.hasPower(AirElement.POWER_ID)) {
            brewPotion(AirElement.POWER_ID, new WeakenPotion());
        } else if (owner.hasPower(DarkElement.POWER_ID)) {
            brewPotion(DarkElement.POWER_ID, new SpeedPotion());
        } else if (owner.hasPower(FireElement.POWER_ID)) {
            brewPotion(FireElement.POWER_ID, new ExplosivePotion());
        } else if (owner.hasPower(LightElement.POWER_ID)) {
            brewPotion(LightElement.POWER_ID, new DexterityPotion());
        } else if (owner.hasPower(MagicElement.POWER_ID)) {
            brewPotion(MagicElement.POWER_ID, new AncientPotion());
        } else if (owner.hasPower(WaterElement.POWER_ID)) {
            brewPotion(WaterElement.POWER_ID, new EssenceOfSteel());
        }

        while (this.amount >= 2) {
            AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(
                    new BlockPotion()
            ));
            this.amount -= 2;
        }

        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, EarthElement.POWER_ID));
        }
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new EarthElement(this.owner, this.source, this.amount);
    }

    @Override
    public AbstractElement makeCopy(int amount) {
        return new EarthElement(this.owner, this.source, amount);
    }
}
