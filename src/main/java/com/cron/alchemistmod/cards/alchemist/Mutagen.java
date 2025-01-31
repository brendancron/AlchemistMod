package com.cron.alchemistmod.cards.alchemist;

import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Mutagen extends AbstractAlchemistCard {
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAlchemist.Enums.ALCHEMIST;

    private static final int COST = 1;
    private static final int MAGIC = 3;
    private static final int MAGIC_UPGRADE = 1;
    private static final int MAGIC_TWO = 3;
    private static final int MAGIC_TWO_UPGRADE = -1;

    public final static String ID = AlchemistMod.makeID(Mutagen.class.getSimpleName());
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = AlchemistMod.makeAlchemistCardPath(Mutagen.class.getSimpleName() + ".png");

    public Mutagen() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = MAGIC;

        this.magicNumberTwo = this.baseMagicNumberTwo = MAGIC_TWO;
        this.isMagicNumberTwoModified = false;
        this.isMagicNumberTwoUpgraded = false;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(MAGIC_UPGRADE);
            this.upgradeMagicNumber2(MAGIC_TWO_UPGRADE);
        }
    }

    public void upgradeMagicNumber2(int amount) {
        this.baseMagicNumberTwo += amount;
        this.magicNumberTwo = this.baseMagicNumberTwo;
        this.isMagicNumberTwoUpgraded = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber)
        );

        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new PoisonPower(p, p, this.magicNumberTwo), this.magicNumberTwo)
        );
    }
}
