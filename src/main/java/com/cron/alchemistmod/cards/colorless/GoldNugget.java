package com.cron.alchemistmod.cards.colorless;

import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.cards.alchemist.Chrysopoeia;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GoldNugget extends AbstractCard {
    public final static String ID = AlchemistMod.makeID(GoldNugget.class.getSimpleName());
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = AlchemistMod.makeAlchemistCardPath("_Undefined.png");

    private static final int MAGIC = 10;
    private static final int MAGIC_UPGRADE = 5;


    public GoldNugget() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, -2, CARD_STRINGS.DESCRIPTION, CardType.STATUS, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.isEthereal = true;
        this.magicNumber = MAGIC;
        this.baseMagicNumber = MAGIC;
    }

    public void triggerOnExhaust() {
        this.addToTop(new GainGoldAction(this.magicNumber));

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(MAGIC_UPGRADE);
        }
    }

    public AbstractCard makeCopy() {
        return new GoldNugget();
    }
}
