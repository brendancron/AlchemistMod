package com.cron.alchemistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.cron.alchemistmod.util.TrackPotions;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PotionBox extends AbstractAlchemistRelic {
    private static final RelicTier RARITY = RelicTier.BOSS;

    public static final String ID = AlchemistMod.makeID(PotionBox.class.getSimpleName());
    public static final RelicStrings RELIC_STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    public int extraPotionSlot = 0;
    public static final int SLOTS_GIVEN = 3;
    private static final Texture IMG = TextureLoader.getTexture(AlchemistMod.makeRelicTexturePath(PotionBox.class.getSimpleName() + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(AlchemistMod.makeRelicOutlinePath(PotionBox.class.getSimpleName() + ".png"));
    public static final Logger logger = LogManager.getLogger(PotionBox.class.getSimpleName());

    public PotionBox() {
        super(ID, IMG, OUTLINE, RARITY, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return RELIC_STRINGS.DESCRIPTIONS[0];
    }

    public void onEquip() {
        if (extraPotionSlot > 0) {
            TrackPotions.addPotionSlot(SLOTS_GIVEN - extraPotionSlot);
            extraPotionSlot = 0;
        } else {
            TrackPotions.addPotionSlot(SLOTS_GIVEN);
        }
    }

    @Override
    public void onUnequip() {
        TrackPotions.removePotionSlot(3);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(PotionBag.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(PotionBag.ID)) {
                    extraPotionSlot += PotionBag.SLOTS_GIVEN;
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(PotionBag.ID);
    }

    public AbstractRelic makeCopy() {
        return new PotionBox();
    }
}

