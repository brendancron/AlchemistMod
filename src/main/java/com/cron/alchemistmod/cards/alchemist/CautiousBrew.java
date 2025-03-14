package com.cron.alchemistmod.cards.alchemist;

import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.powers.EarthElement;
import com.cron.alchemistmod.powers.FireElement;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CautiousBrew extends AbstractAlchemistCard {
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAlchemist.Enums.ALCHEMIST;

    private static final int COST = 2;
    private static final int MAGIC = 2;
    private static final int MAGIC_UPGRADE = 1;

    public final static String ID = AlchemistMod.makeID(CautiousBrew.class.getSimpleName());
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = AlchemistMod.makeAlchemistCardPath(CautiousBrew.class.getSimpleName() + ".png");

    public CautiousBrew() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = MAGIC;
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
        if (m != null && m.getIntentBaseDmg() >= 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new EarthElement(p, p, this.magicNumber), this.magicNumber)
            );
        } else {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new FireElement(p, p, this.magicNumber), this.magicNumber)
            );
        }
    }
}
