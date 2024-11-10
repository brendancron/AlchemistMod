package com.cron.alchemistmod.cards;

import basemod.abstracts.CustomCard;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.powers.FireElement;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Explosion extends CustomCard {
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAlchemist.Enums.COLOR_GRAY;

    private static final int COST = -1;

    public final static String ID = AlchemistMod.makeID(Explosion.class.getSimpleName());
    public static final String NAME = CardCrawlGame.languagePack.getCardStrings(ID).NAME;
    public static final String DESCRIPTION = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = CardCrawlGame.languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = AlchemistMod.makeCardPath(Explosion.class.getSimpleName() + ".png");

    public Explosion() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new FireElement(p, p, this.energyOnUse + 1), this.energyOnUse + 1)
            );
        } else {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new FireElement(p, p, this.energyOnUse), this.energyOnUse)
            );
        }
    }
}
