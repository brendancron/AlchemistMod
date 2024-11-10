package com.cron.alchemistmod.powers;


import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class ElementalFormPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = AlchemistMod.makeID(ElementalFormPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(AlchemistMod.makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(AlchemistMod.makePowerPath("placeholder_power32.png"));

    public ElementalFormPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        int amount = this.amount * power.amount;

        if (power instanceof AirElement) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(target, source, new DrawCardNextTurnPower(target, amount), amount)
            );
        } else if (power instanceof DarkElement) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(target, source, new EnergizedPower(target, amount), amount)
            );
        } else if (power instanceof EarthElement) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(target, source, new PlatedArmorPower(target, amount), amount)
            );
        } else if (power instanceof FireElement) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(target, source, new VigorPower(target, amount), amount)
            );
        } else if (power instanceof LightElement) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(target, source, new BlurPower(target, amount), amount)
            );
        } else if (power instanceof MagicElement) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(target, source, new BufferPower(target, amount), amount)
            );
        } else if (power instanceof WaterElement) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(target, source, new RagePower(target, amount), amount)
            );
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new ElementalFormPower(owner, source, amount);
    }
}
