package com.cron.alchemistmod.cards.util;

import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.cron.alchemistmod.cards.alchemist.BagOfBricks;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DamageCalculationCard extends AbstractAlchemistCard {
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAlchemist.Enums.ALCHEMIST;

    private static final int COST = 1;
    private static final int DAMAGE = 10;
    private static final boolean MULTI_DAMAGE = true;


    public final static String ID = AlchemistMod.makeID(BagOfBricks.class.getSimpleName());
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = AlchemistMod.makeAlchemistCardPath(BagOfBricks.class.getSimpleName() + ".png");
    public static final Logger logger = LogManager.getLogger(DamageCalculationCard.class.getSimpleName());

    public DamageCalculationCard() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.isMultiDamage = MULTI_DAMAGE;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public int getLowestMultidamage(int baseDamage) {
        int minDamage = Integer.MAX_VALUE;
        for (Integer damage : getMultidamage(baseDamage)) {
            minDamage = Math.min(minDamage, damage);
        }

        return minDamage;
    }
    public int[] getMultidamage(int baseDamage) {
        this.isMultiDamage = true;
        this.baseDamage = this.damage = baseDamage;
        calculateCardDamage(AbstractDungeon.getRandomMonster());
        return this.multiDamage;
    }
    public int getDamage(int baseDamage, AbstractMonster target) {
        this.isMultiDamage = false;
        this.baseDamage = this.damage = baseDamage;
        calculateCardDamage(target);
        return this.damage;
    }
}
