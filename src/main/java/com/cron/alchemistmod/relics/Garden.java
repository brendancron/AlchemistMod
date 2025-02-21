package com.cron.alchemistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.cards.colorless.*;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Garden extends AbstractAlchemistRelic {
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final CardGroup gardenPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);

    public static final String ID = AlchemistMod.makeID(Garden.class.getSimpleName());
    public static final RelicStrings RELIC_STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final Texture IMG = TextureLoader.getTexture(AlchemistMod.makeRelicTexturePath(Garden.class.getSimpleName() + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(AlchemistMod.makeRelicOutlinePath(Garden.class.getSimpleName() + ".png"));
    public static final Logger logger = LogManager.getLogger(Garden.class.getSimpleName());

    public Garden() {
        super(ID, IMG, OUTLINE, RARITY, LandingSound.FLAT);
        populateGardenPool();
    }

    public String getUpdatedDescription() {
        return RELIC_STRINGS.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        AbstractDungeon.actionManager.addToBottom(
                new MakeTempCardInHandAction(gardenPool.getRandomCard(true).makeCopy(), 1, false)
        );
    }

    public AbstractRelic makeCopy() {
        return new Garden();
    }

    public static void populateGardenPool() {
        if (gardenPool.isEmpty()) {
            gardenPool.addToBottom(new AirElementCard());
            gardenPool.addToBottom(new DarkElementCard());
            gardenPool.addToBottom(new EarthElementCard());
            gardenPool.addToBottom(new FireElementCard());
            gardenPool.addToBottom(new LightElementCard());
            gardenPool.addToBottom(new WaterElementCard());
        }
    }
}

