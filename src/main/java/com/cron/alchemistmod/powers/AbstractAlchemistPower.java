package com.cron.alchemistmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class AbstractAlchemistPower extends AbstractPower implements CloneablePowerInterface {
    public abstract AbstractAlchemistPower makeCopy(int amount);
    public void onCreateCard(AbstractCard card) {
    }
}
