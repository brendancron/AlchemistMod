package com.cron.alchemistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrimaryCollection extends AbstractAlchemistRelic {
    private static final RelicTier RARITY = RelicTier.RARE;

    public static final String ID = AlchemistMod.makeID(PrimaryCollection.class.getSimpleName());
    public static final RelicStrings RELIC_STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final Texture IMG = TextureLoader.getTexture(AlchemistMod.makeRelicTexturePath(PrimaryCollection.class.getSimpleName() + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(AlchemistMod.makeRelicOutlinePath(PrimaryCollection.class.getSimpleName() + ".png"));
    public static final Logger logger = LogManager.getLogger(PrimaryCollection.class.getSimpleName());

    public PrimaryCollection() {
        super(ID, IMG, OUTLINE, RARITY, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return RELIC_STRINGS.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new PrimaryCollection();
    }
}

