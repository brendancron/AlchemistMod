package com.cron.alchemistmod.cards.alchemist;

import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.actions.SelectCardsCenteredAction;
import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.cron.alchemistmod.cards.colorless.AirElementCard;
import com.cron.alchemistmod.cards.colorless.EarthElementCard;
import com.cron.alchemistmod.cards.colorless.FireElementCard;
import com.cron.alchemistmod.cards.colorless.WaterElementCard;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.powers.AbstractElement;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class ElementalBurst extends AbstractAlchemistCard {
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAlchemist.Enums.ALCHEMIST;

    public static final String CHOICE_STRING = "Choose one.";

    private static final int COST = 1;

    public static final String ID = AlchemistMod.makeID(ElementalBurst.class.getSimpleName());
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    public ElementalBurst() {
        super(ID, CARD_STRINGS.NAME, ElementalBurst.class, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if (upgraded) {
            ArrayList<AbstractCard> choices = new ArrayList<>();
            choices.add(new AirElementCard());
            choices.add(new EarthElementCard());
            choices.add(new WaterElementCard());
            choices.add(new FireElementCard());
            AbstractDungeon.actionManager.addToBottom(new SelectCardsCenteredAction(choices, 1, CHOICE_STRING, (cards) -> {
                AbstractCard chosen = cards.get(0).makeStatEquivalentCopy();
                addToTop(new MakeTempCardInHandAction(chosen, true));
            }));
        } else {
            AbstractElement element = AbstractElement.getRandomBasicElement(p, p, 1);
            AbstractDungeon.actionManager.addToBottom(
                new MakeTempCardInHandAction(element.element.getCard(), 1, false)
            );
        }
    }
}
