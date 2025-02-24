package com.cron.alchemistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VirulentPoison extends AbstractAlchemistRelic {
    private static final RelicTier RARITY = RelicTier.RARE;

    public static final String ID = AlchemistMod.makeID(VirulentPoison.class.getSimpleName());
    public static final RelicStrings RELIC_STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final Texture IMG = TextureLoader.getTexture(AlchemistMod.makeRelicTexturePath(VirulentPoison.class.getSimpleName() + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(AlchemistMod.makeRelicOutlinePath(VirulentPoison.class.getSimpleName() + ".png"));
    public static final Logger logger = LogManager.getLogger(VirulentPoison.class.getSimpleName());

    public VirulentPoison() {
        super(ID, IMG, OUTLINE, RARITY, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return RELIC_STRINGS.DESCRIPTIONS[0];
    }

    @Override
    public void onPlayerEndTurn() {
        for(AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            for(AbstractPower power : monster.powers) {
                if (power instanceof PoisonPower) {
                    power.atStartOfTurn();
                }
            }
        }
    }

    public AbstractRelic makeCopy() {
        return new VirulentPoison();
    }
}

