package com.cron.alchemistmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class AbstractAlchemistPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;
    public abstract AbstractAlchemistPower makeCopy(int amount);
    public void onCreateCard(AbstractCard card) {
    }
    public void onGainElement(AbstractElement element) {
    }
    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power instanceof AbstractElement) {
            onGainElement((AbstractElement) power);
        }
    }

    public void onObtainPotion(AbstractPotion potion) {
    }

    public void onUsePotion(AbstractPotion potion) {
    }

    public void onDiscardPotion(AbstractPotion potion) {
    }

    public void onAnyPowerApplied(AbstractPower power, AbstractCreature target, AbstractCreature source) {

    }
}
