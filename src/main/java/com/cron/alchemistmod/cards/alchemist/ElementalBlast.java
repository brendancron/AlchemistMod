package com.cron.alchemistmod.cards.alchemist;

import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.powers.AbstractElement;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ElementalBlast extends AbstractAlchemistCard {
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAlchemist.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int DAMAGE_UPGRADE = 3;
    private static final boolean MULTI_DAMAGE = true;

    public final static String ID = AlchemistMod.makeID(ElementalBlast.class.getSimpleName());
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = AlchemistMod.makeAlchemistCardPath(ElementalBlast.class.getSimpleName() + ".png");

    public ElementalBlast() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.isMultiDamage = MULTI_DAMAGE;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(DAMAGE_UPGRADE);
            this.initializeDescription();
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (AbstractElement.hasElement()) {
            for (int i = 0; i < this.multiDamage.length; i++) {
                this.multiDamage[i] = this.multiDamage[i] * 2;
            }

            this.damage = this.multiDamage[0];

            this.isDamageModified = this.baseDamage != this.damage;
        }

        this.rawDescription = CARD_STRINGS.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);

        if (AbstractElement.hasElement()) {
            for (int i = 0; i < this.multiDamage.length; i++) {
                this.multiDamage[i] = this.multiDamage[i] * 2;
            }

            this.damage = this.multiDamage[0];

            this.isDamageModified = this.baseDamage != this.damage;
        }

        this.rawDescription = CARD_STRINGS.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE)
        );
    }
}
