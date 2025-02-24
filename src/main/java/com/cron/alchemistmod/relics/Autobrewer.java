package com.cron.alchemistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.actions.GainRandomPotionAction;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Autobrewer extends AbstractAlchemistRelic {
    private static final RelicTier RARITY = RelicTier.COMMON;

    public static final String ID = AlchemistMod.makeID(Autobrewer.class.getSimpleName());
    public static final RelicStrings RELIC_STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final Texture IMG = TextureLoader.getTexture(AlchemistMod.makeRelicTexturePath(Autobrewer.class.getSimpleName() + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(AlchemistMod.makeRelicOutlinePath(Autobrewer.class.getSimpleName() + ".png"));
    public static final Logger logger = LogManager.getLogger(Autobrewer.class.getSimpleName());

    public Autobrewer() {
        super(ID, IMG, OUTLINE, RARITY, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return RELIC_STRINGS.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(
                new GainRandomPotionAction(1)
        );
    }

    public AbstractRelic makeCopy() {
        return new Autobrewer();
    }
}

