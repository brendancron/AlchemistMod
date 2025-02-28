package com.cron.alchemistmod.powers;

import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.cron.alchemistmod.relics.Distillery;
import com.cron.alchemistmod.util.Element;
import com.cron.alchemistmod.util.PotionElements;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class AbstractElement extends AbstractAlchemistPower {
    public final Element element;
    public AbstractElement(final AbstractCreature owner, final AbstractCreature source, final int amount, final Element element) {
        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.element = element;

        this.type = PowerType.BUFF;
        this.isTurnBased = false;
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        testForPotions();
        triggerOnGainElement();
    }
    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        testForPotions();
        triggerOnGainElement();
    }

    public void triggerOnGainElement() {
        triggerOnGainElement(AbstractDungeon.player.drawPile);
        triggerOnGainElement(AbstractDungeon.player.discardPile);
        triggerOnGainElement(AbstractDungeon.player.hand);
        triggerOnGainElement(AbstractDungeon.player.exhaustPile);
    }
    public void triggerOnGainElement(CardGroup group) {
        for (AbstractCard card: group.group) {
            if (card instanceof AbstractAlchemistCard) {
                ((AbstractAlchemistCard) card).triggerOnGainElement(this);
            }
        }
    }

    public static boolean hasElement() {
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof AbstractElement) {
                return true;
            }
        }
        return false;
    }
    public static AbstractElement getElement() {
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof AbstractElement) {
                return (AbstractElement) power;
            }
        }
        return null;
    }

    public static AbstractElement getRandomElement(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        int elementNum = AbstractDungeon.cardRandomRng.random(0, 6);

        switch (elementNum) {
            case 0: return new AirElement(owner, source, amount);
            case 1: return new DarkElement(owner, source, amount);
            case 2: return new EarthElement(owner, source, amount);
            case 3: return new FireElement(owner, source, amount);
            case 4: return new LightElement(owner, source, amount);
            case 5: return new MagicElement(owner, source, amount);
            default: return new WaterElement(owner, source, amount);
        }
    }

    public static AbstractElement getRandomBasicElement(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        int elementNum = AbstractDungeon.cardRandomRng.random(0, 3);

        switch (elementNum) {
            case 0: return new AirElement(owner, source, amount);
            case 1: return new EarthElement(owner, source, amount);
            case 2: return new FireElement(owner, source, amount);
            default: return new WaterElement(owner, source, amount);
        }
    }

    public void testForPotions() {
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof AbstractElement && power != this) {
                AbstractDungeon.actionManager.addToBottom(
                        new ObtainPotionAction(PotionElements.getPotion(((AbstractElement) power).element, this.element))
                );

                power.amount -= 1;
                if (!AbstractDungeon.player.hasRelic(Distillery.ID)) {
                    this.amount -= 1;
                }

                if (power.amount == 0) {
                    AbstractDungeon.actionManager.addToTop(
                            new RemoveSpecificPowerAction(this.owner, this.owner, power)
                    );
                }
                break;
            }
        }

        while (this.amount >= 2) {
            AbstractDungeon.actionManager.addToBottom(
                    new ObtainPotionAction(PotionElements.getPotion(this.element, this.element))
            );
            if (AbstractDungeon.player.hasRelic(Distillery.ID)) {
                this.amount -= 1;
            } else {
                this.amount -= 2;
            }
        }

        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(
                    new RemoveSpecificPowerAction(this.owner, this.owner, this)
            );
        }
    }
}
