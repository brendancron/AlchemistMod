package com.cron.alchemistmod.relics;

import basemod.abstracts.CustomRelic;
import com.cron.alchemistmod.AlchemistMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class PotionBag extends CustomRelic {
    public static final String ID = AlchemistMod.makeID(PotionBag.class.getSimpleName());
    public static final RelicStrings RELIC_STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);

    public PotionBag() {
        super(ID, "cauldron.png", RelicTier.STARTER, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return RELIC_STRINGS.DESCRIPTIONS[0];
    }

    public void onEquip() {
        AbstractDungeon.player.potionSlots += 1;
        AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));
    }

    public boolean canSpawn() {
        return false;
    }

    public AbstractRelic makeCopy() {
        return new PotionBag();
    }
}

