package com.cron.alchemistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.powers.PotionPotencyPower;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PotionLauncher extends AbstractAlchemistRelic {
    private static final RelicTier RARITY = RelicTier.UNCOMMON;

    public static final String ID = AlchemistMod.makeID(PotionLauncher.class.getSimpleName());
    public static final RelicStrings RELIC_STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final Texture IMG = TextureLoader.getTexture(AlchemistMod.makeRelicTexturePath(PotionLauncher.class.getSimpleName() + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(AlchemistMod.makeRelicOutlinePath(PotionLauncher.class.getSimpleName() + ".png"));
    public static final Logger logger = LogManager.getLogger(PotionLauncher.class.getSimpleName());

    private static final int POTENCY_AMOUNT = 5;

    public PotionLauncher() {
        super(ID, IMG, OUTLINE, RARITY, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return RELIC_STRINGS.DESCRIPTIONS[0] + POTENCY_AMOUNT + RELIC_STRINGS.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                    AbstractDungeon.player,
                    AbstractDungeon.player,
                    new PotionPotencyPower(AbstractDungeon.player, AbstractDungeon.player, POTENCY_AMOUNT),
                    POTENCY_AMOUNT
                )
        );
    }

    public AbstractRelic makeCopy() {
        return new PotionLauncher();
    }
}

