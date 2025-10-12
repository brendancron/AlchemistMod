package com.cron.alchemistmod.cards.alchemist;

import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.powers.AbstractElement;
import com.cron.alchemistmod.powers.EarthElement;
import com.cron.alchemistmod.powers.FireElement;
import com.cron.alchemistmod.powers.PackedEarthPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PackedEarth extends AbstractAlchemistCard {
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAlchemist.Enums.ALCHEMIST;

    private static final int COST = 1;
    private static final int MAGIC = 1;
    private static final int MAGIC_UPGRADE = 1;

    public final static String ID = AlchemistMod.makeID(PackedEarth.class.getSimpleName());
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = AlchemistMod.makeAlchemistCardPath(PackedEarth.class.getSimpleName() + ".png");

    public PackedEarth() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(MAGIC_UPGRADE);
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = this.magicNumber;
        if (AbstractElement.hasElement(EarthElement.class)) {
            amount += 2;
        }
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(p, p, new PackedEarthPower(p, p, amount), amount)
        );
    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractElement.hasElement(EarthElement.class)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }
}
