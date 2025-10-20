package com.cron.alchemistmod.cards.alchemist;

import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.actions.SelectCardsCenteredAction;
import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.powers.AbstractElement;
import com.cron.alchemistmod.powers.WaterElement;
import com.cron.alchemistmod.util.CustomTags;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Collections;

public class AquaVitae extends AbstractAlchemistCard {
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAlchemist.Enums.ALCHEMIST;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    public static final String ID = AlchemistMod.makeID(AquaVitae.class.getSimpleName());
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String CHOICE_STRING = "Choose one.";

    public AquaVitae() {
        super(ID, CARD_STRINGS.NAME, AquaVitae.class, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.tags.add(CustomTags.WATER_ELEMENT);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean freeThisTurn = AbstractElement.hasElement(WaterElement.class);

        ArrayList<AbstractCard> pool = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (c.color == TheAlchemist.Enums.ALCHEMIST
                    && c.rarity != CardRarity.SPECIAL
                    && c.type != AbstractCard.CardType.CURSE
                    && c.type != AbstractCard.CardType.STATUS
                    && !c.hasTag(CardTags.HEALING)) {
                pool.add(c.makeCopy());
            }
        }

        Collections.shuffle(pool, AbstractDungeon.cardRandomRng.random);
        ArrayList<AbstractCard> choices = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            choices.add(pool.get(i).makeCopy());
        }

        AbstractDungeon.actionManager.addToBottom(new SelectCardsCenteredAction(choices, 1, CHOICE_STRING, (cards) -> {
            AbstractCard chosen = cards.get(0).makeStatEquivalentCopy();
            if (freeThisTurn) {
                chosen.setCostForTurn(0);
            }
            addToTop(new MakeTempCardInHandAction(chosen, true));
        }));

    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractElement.hasElement(WaterElement.class)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
}
