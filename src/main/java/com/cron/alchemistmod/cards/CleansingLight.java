package com.cron.alchemistmod.cards;

import basemod.abstracts.CustomCard;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.actions.RemovePotionAction;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.powers.LightElement;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CleansingLight extends CustomCard {
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAlchemist.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int MAGIC = 1;
    private static final int MAGIC_UPGRADE = 1;

    public final static String ID = AlchemistMod.makeID(CleansingLight.class.getSimpleName());
    public static final String NAME = CardCrawlGame.languagePack.getCardStrings(ID).NAME;
    public static final String DESCRIPTION = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
    public static final String IMG_PATH = AlchemistMod.makeCardPath(CleansingLight.class.getSimpleName() + ".png");

    public CleansingLight() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = MAGIC;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(MAGIC_UPGRADE);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new RemovePotionAction(p)
        );
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new LightElement(p, p, magicNumber), magicNumber)
        );
    }
}