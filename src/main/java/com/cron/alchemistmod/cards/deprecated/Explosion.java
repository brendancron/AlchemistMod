package com.cron.alchemistmod.cards.deprecated;

import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.powers.AbstractElement;
import com.cron.alchemistmod.powers.FireElement;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
    private static final int DAMAGE = 4;
    private static final int DAMAGE_UPGRADE = 1;
    private static final int MAGIC = 1;

    public final static String ID = AlchemistMod.makeID(Explosion.class.getSimpleName());
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = AlchemistMod.makeAlchemistCardPath(Explosion.class.getSimpleName() + ".png");

    public Explosion() {
        super(ID, CARD_STRINGS.NAME, Explosion.class, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;

        this.magicNumber = this.baseMagicNumber = MAGIC;
        this.baseDamage = DAMAGE;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(DAMAGE_UPGRADE);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int hits = this.energyOnUse;
        if (AbstractElement.hasElement(FireElement.class)) {
            hits *= 2;
        }
        for (int i = 0; i < hits; i++) {
            AbstractDungeon.actionManager.addToBottom(
                    new AttackDamageRandomEnemyAction(
                            this,
                            AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractElement.hasElement(FireElement.class)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
}
