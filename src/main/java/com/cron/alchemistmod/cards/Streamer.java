package com.cron.alchemistmod.cards;

import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.util.TrackPotions;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
    private int emptyPotionSlots;

    public final static String ID = AlchemistMod.makeID(Streamer.class.getSimpleName());
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = AlchemistMod.makeCardPath(Streamer.class.getSimpleName() + ".png");

    public Streamer() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST + TrackPotions.numOfEmptyPotionSlots(), CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.emptyPotionSlots = TrackPotions.numOfEmptyPotionSlots();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(BLOCK_UPGRADE);
        }
    }

    public void applyPowers() {
        super.applyPowers();
        updateCostPotions();
    }

    @Override
    public void triggerOnObtainPotion(AbstractPotion potion) {
        updateCostPotions();
    }

    @Override
    public void triggerOnUsePotion(AbstractPotion potion) {
        updateCostPotions();
    }

    @Override
    public void triggerOnDiscardPotion(AbstractPotion potion) {
        updateCostPotions();
    }

    public void updateCostPotions() {
        int emptyPotionSlots = TrackPotions.numOfEmptyPotionSlots();
        setCostForTurn(this.costForTurn + emptyPotionSlots - this.emptyPotionSlots);
        this.emptyPotionSlots = emptyPotionSlots;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(p, p, block)
        );
    }
}
