package com.cron.alchemistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.cron.alchemistmod.util.TrackPotions;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class PotionBag extends AbstractAlchemistRelic {
    public static final String ID = AlchemistMod.makeID(PotionBag.class.getSimpleName());
    public static final RelicStrings RELIC_STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final Texture IMG = TextureLoader.getTexture(AlchemistMod.makeRelicTexturePath(PotionBag.class.getSimpleName() + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(AlchemistMod.makeRelicOutlinePath(PotionBag.class.getSimpleName() + ".png"));

    public PotionBag() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return RELIC_STRINGS.DESCRIPTIONS[0];
    }

    public void onEquip() {
        TrackPotions.addPotionSlot(1);
    }

    @Override
    public void onRemove() {
        TrackPotions.removePotionSlot(1);
    }

    public boolean canSpawn() {
        return false;
    }

    public AbstractRelic makeCopy() {
        return new PotionBag();
    }
}

