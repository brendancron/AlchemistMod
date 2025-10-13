package com.cron.alchemistmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class AbstractAlchemistPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    protected void configureTexture(Class<?> clazz) {
        String baseName = clazz.getSimpleName();
        String tex84Path = AlchemistMod.makePowerPath(baseName + "84.png");
        String tex32Path = AlchemistMod.makePowerPath(baseName + "32.png");

        Texture tex84 = tryLoadWithFallback(tex84Path, AlchemistMod.makePowerPath("_Undefined84.png"));
        Texture tex32 = tryLoadWithFallback(tex32Path, AlchemistMod.makePowerPath("_Undefined32.png"));

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    private static Texture tryLoadWithFallback(String path, String fallbackPath) {
        if (path == null || !Gdx.files.internal(path).exists()) {
            AlchemistMod.logger.info("Missing power texture: {}", path);
            path = fallbackPath;
        }
        return TextureLoader.getTexture(path);
    }

    public abstract AbstractAlchemistPower makeCopy(int amount);
    public void onCreateCard(AbstractCard card) {
    }
    public void onGainElement(AbstractElement element) {
    }
    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power instanceof AbstractElement) {
            onGainElement((AbstractElement) power);
        }
    }

    public void onObtainPotion(AbstractPotion potion) {
    }

    public void onUsePotion(AbstractPotion potion) {
    }

    public void onDiscardPotion(AbstractPotion potion) {
    }

    public void onAnyPowerApplied(AbstractPower power, AbstractCreature target, AbstractCreature source) {

    }

}
