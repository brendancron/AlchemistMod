package com.cron.alchemistmod.cards.alchemist;

import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.actions.SelectAndPayForCardAction;
import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.powers.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EchoesOfDarkness extends AbstractAlchemistCard {
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAlchemist.Enums.ALCHEMIST;

    private static final int COST = 0;
    private static final int MAGIC = 2;

    public static final String ID = AlchemistMod.makeID(EchoesOfDarkness.class.getSimpleName());
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    public EchoesOfDarkness() {
        super(ID, CARD_STRINGS.NAME, EchoesOfDarkness.class, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = MAGIC;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int stacks = this.magicNumber;
        if (AbstractElement.hasElement(DarkElement.class)) {
            stacks += 3;
        }
        int finalStacks = stacks;
        AbstractDungeon.actionManager.addToBottom(
            new SelectAndPayForCardAction(p, (echoedCard) -> {
                AbstractDungeon.actionManager.addToBottom(
                    new ExhaustSpecificCardAction(echoedCard, AbstractDungeon.player.hand)
                );
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(
                                p,
                                p,
                                new EchoedCardPower(p, p, finalStacks, echoedCard),
                                finalStacks
                        )
                );
            })
        );
    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractElement.hasElement(DarkElement.class)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
}
