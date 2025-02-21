package com.cron.alchemistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.cron.alchemistmod.util.TrackPotions;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PotionBag extends AbstractAlchemistRelic {
    private static final RelicTier RARITY = RelicTier.STARTER;
    public static final int SLOTS_GIVEN = 1;

    public static final String ID = AlchemistMod.makeID(PotionBag.class.getSimpleName());
    public static final RelicStrings RELIC_STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final Texture IMG = TextureLoader.getTexture(AlchemistMod.makeRelicTexturePath(PotionBag.class.getSimpleName() + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(AlchemistMod.makeRelicOutlinePath(PotionBag.class.getSimpleName() + ".png"));
    public static final Logger logger = LogManager.getLogger(PotionBag.class.getSimpleName());

    public PotionBag() {
        super(ID, IMG, OUTLINE, RARITY, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return RELIC_STRINGS.DESCRIPTIONS[0];
    }

    public void onEquip() {
        TrackPotions.addPotionSlot(SLOTS_GIVEN);
    }

    @Override
    public void onRemove() {
        TrackPotions.removePotionSlot(SLOTS_GIVEN);
    }

    @Override
    public boolean canSpawn() {
        return false;
    }

    public AbstractRelic makeCopy() {
        return new PotionBag();
    }
}

