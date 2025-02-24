package com.cron.alchemistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.actions.BlindBrewAction;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlindBrew extends AbstractAlchemistRelic {
    private static final RelicTier RARITY = RelicTier.SHOP;

    public static final String ID = AlchemistMod.makeID(BlindBrew.class.getSimpleName());
    public static final RelicStrings RELIC_STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final Texture IMG = TextureLoader.getTexture(AlchemistMod.makeRelicTexturePath(BlindBrew.class.getSimpleName() + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(AlchemistMod.makeRelicOutlinePath(BlindBrew.class.getSimpleName() + ".png"));
    public static final Logger logger = LogManager.getLogger(BlindBrew.class.getSimpleName());

    public BlindBrew() {
        super(ID, IMG, OUTLINE, RARITY, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return RELIC_STRINGS.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStartPostDraw() {
        AbstractDungeon.actionManager.addToBottom(
                new BlindBrewAction()
        );
    }

    public AbstractRelic makeCopy() {
        return new BlindBrew();
    }
}

