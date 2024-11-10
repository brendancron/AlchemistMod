package com.cron.alchemistmod.cards;

import basemod.abstracts.CustomCard;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.powers.ElementalFormPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ElementalForm extends CustomCard {
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheAlchemist.Enums.COLOR_GRAY;

    private static final int COST = 3;
    private static final int MAGIC = 1;

    public final static String ID = AlchemistMod.makeID(ElementalForm.class.getSimpleName());
    public static final String NAME = CardCrawlGame.languagePack.getCardStrings(ID).NAME;
    public static final String DESCRIPTION = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = CardCrawlGame.languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = AlchemistMod.makeCardPath(ElementalForm.class.getSimpleName() + ".png");

    public ElementalForm() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = MAGIC;
        this.isEthereal = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isEthereal = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new ElementalFormPower(p, p, magicNumber), magicNumber)
        );
    }
}
