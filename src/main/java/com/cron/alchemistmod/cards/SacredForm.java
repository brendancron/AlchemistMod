package com.cron.alchemistmod.cards;

import basemod.helpers.BaseModCardTags;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.powers.PoisonedPotionsPower;
import com.cron.alchemistmod.powers.SacredFormPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SacredForm extends AbstractAlchemistCard {
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheAlchemist.Enums.COLOR_GRAY;

    private static final int COST = 3;

    public final static String ID = AlchemistMod.makeID(SacredForm.class.getSimpleName());
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = AlchemistMod.makeCardPath(SacredForm.class.getSimpleName() + ".png");

    public SacredForm() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.isEthereal = true;
        this.tags.add(BaseModCardTags.FORM);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isEthereal = false;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new SacredFormPower(p, p, 1), 1)
        );
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new PoisonedPotionsPower(p, p, 1), 1)
        );
    }
}
