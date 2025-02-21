package com.cron.alchemistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MagicBarrier extends AbstractAlchemistRelic {
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final int AMOUNT = 3;

    public static final String ID = AlchemistMod.makeID(MagicBarrier.class.getSimpleName());
    public static final RelicStrings RELIC_STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final Texture IMG = TextureLoader.getTexture(AlchemistMod.makeRelicTexturePath(MagicBarrier.class.getSimpleName() + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(AlchemistMod.makeRelicOutlinePath(MagicBarrier.class.getSimpleName() + ".png"));
    public static final Logger logger = LogManager.getLogger(MagicBarrier.class.getSimpleName());

    public MagicBarrier() {
        super(ID, IMG, OUTLINE, RARITY, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return RELIC_STRINGS.DESCRIPTIONS[0];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (source instanceof AbstractPlayer) {
            AbstractDungeon.actionManager.addToBottom(
                    new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, AMOUNT)
            );
        }
    }

    public AbstractRelic makeCopy() {
        return new MagicBarrier();
    }
}

