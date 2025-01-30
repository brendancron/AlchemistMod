package com.cron.alchemistmod.cards.alchemist;

import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.util.TrackPotions;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class Streamer extends AbstractAlchemistCard {
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAlchemist.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int BLOCK = 12;
    private static final int BLOCK_UPGRADE = 4;

    public final static String ID = AlchemistMod.makeID(Streamer.class.getSimpleName());
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = AlchemistMod.makeAlchemistCardPath(Streamer.class.getSimpleName() + ".png");

    public Streamer() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST + TrackPotions.numOfEmptyPotionSlots(), CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(BLOCK_UPGRADE);
        }
    }

    @Override
    public void triggerOnObtainPotion(AbstractPotion potion) {
        this.updateCost(-1);
    }

    @Override
    public void triggerOnUsePotion(AbstractPotion potion) {
        this.updateCost(1);
    }

    @Override
    public void triggerOnDiscardPotion(AbstractPotion potion) {
        this.updateCost(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(p, p, block)
        );
    }

    @Override
    public AbstractCard makeCopy() {
        AbstractCard newCard = new Streamer();
        if (AbstractDungeon.player != null) {
            AlchemistMod.logger.info("num of empty slots is " + TrackPotions.numOfEmptyPotionSlots());
            newCard.updateCost(TrackPotions.numOfEmptyPotionSlots());
        }

        return newCard;
    }
}
