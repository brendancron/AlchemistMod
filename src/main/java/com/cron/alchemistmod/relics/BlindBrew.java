package com.cron.alchemistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.potions.*;
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
    public void atTurnStart() {
        AbstractPotion potion = limitedRandomPotion();
        logger.info(potion.ID);
        potion.use(AbstractDungeon.player);
    }

    public AbstractRelic makeCopy() {
        return new BlindBrew();
    }

    public AbstractPotion limitedRandomPotion() {
        int roll = AbstractDungeon.potionRng.random(0, 99);
        AbstractPotion.PotionRarity rarity;
        if (roll < PotionHelper.POTION_COMMON_CHANCE) {
            rarity = AbstractPotion.PotionRarity.COMMON;
        } else if (roll < PotionHelper.POTION_UNCOMMON_CHANCE + PotionHelper.POTION_COMMON_CHANCE) {
            rarity = AbstractPotion.PotionRarity.UNCOMMON;
        } else {
            rarity = AbstractPotion.PotionRarity.RARE;
        }

        AbstractPotion potion = PotionHelper.getRandomPotion();
        boolean badPotion = true;
        while (badPotion) {
            if (
                    !(potion instanceof FairyPotion) &&
                    !(potion instanceof BlessingOfTheForge && AbstractDungeon.player.hand.isEmpty()) &&
                    !(potion instanceof RegenPotion) &&
                    !(potion instanceof SmokeBomb) &&
                    !(potion instanceof BloodPotion) &&
                    potion.rarity == rarity
            ) {
                badPotion = false;
            } else {
                potion = PotionHelper.getRandomPotion();
            }
        }

        return potion;
    }
}

