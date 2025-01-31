package com.cron.alchemistmod.cards.alchemist;

import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.actions.ExplosionAction;
import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Explosion extends AbstractAlchemistCard {
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAlchemist.Enums.ALCHEMIST;

    private static final int COST = -1;
    private static final int MAGIC = 2;
    private static final int MAGIC_UPGRADE = 1;

    public final static String ID = AlchemistMod.makeID(Explosion.class.getSimpleName());
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = AlchemistMod.makeAlchemistCardPath(Explosion.class.getSimpleName() + ".png");

    public Explosion() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
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
        AbstractDungeon.actionManager.addToBottom(
                new ExplosionAction(p, this.magicNumber, this.freeToPlayOnce, this.energyOnUse)
        );
    }
}
