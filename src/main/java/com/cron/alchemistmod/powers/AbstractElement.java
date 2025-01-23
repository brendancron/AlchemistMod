package com.cron.alchemistmod.powers;

import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class AbstractElement extends AbstractAlchemistPower {
    public AbstractElement(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.owner = owner;
        this.amount = amount;
        this.source = source;

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

    public void brewPotion(String elementID, AbstractPotion potion) {
        AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(potion));
        AbstractPower element = owner.getPower(elementID);

        element.amount -= 1;
        this.amount -= 1;

        if (element.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, elementID));
        }
    }
    public abstract void testForPotions();
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

    public static boolean hasElement(AbstractPlayer player) {
        if (player.hasPower(AirElement.POWER_ID)) {
            return true;
        } else if (player.hasPower(DarkElement.POWER_ID)) {
            return true;
        } else if (player.hasPower(EarthElement.POWER_ID)) {
            return true;
        } else if (player.hasPower(FireElement.POWER_ID)) {
            return true;
        } else if (player.hasPower(LightElement.POWER_ID)) {
            return true;
        } else if (player.hasPower(MagicElement.POWER_ID)) {
            return true;
        } else if (player.hasPower(WaterElement.POWER_ID)) {
            return true;
        } else {
            return false;
        }
    }
    public static AbstractElement getElement(AbstractPlayer player) {
        if (player.hasPower(AirElement.POWER_ID)) {
            return (AbstractElement) player.getPower(AirElement.POWER_ID);
        } else if (player.hasPower(DarkElement.POWER_ID)) {
            return (AbstractElement) player.getPower(DarkElement.POWER_ID);
        } else if (player.hasPower(EarthElement.POWER_ID)) {
            return (AbstractElement) player.getPower(EarthElement.POWER_ID);
        } else if (player.hasPower(FireElement.POWER_ID)) {
            return (AbstractElement) player.getPower(FireElement.POWER_ID);
        } else if (player.hasPower(LightElement.POWER_ID)) {
            return (AbstractElement) player.getPower(LightElement.POWER_ID);
        } else if (player.hasPower(MagicElement.POWER_ID)) {
            return (AbstractElement) player.getPower(MagicElement.POWER_ID);
        } else if (player.hasPower(WaterElement.POWER_ID)) {
            return (AbstractElement) player.getPower(WaterElement.POWER_ID);
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
}
