package com.cron.alchemistmod.util;

import basemod.abstracts.DynamicVariable;
import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class MagicNumber2 extends DynamicVariable {

    @Override
    public String key() {
        return "thealchemist:M";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractAlchemistCard) card).isMagicNumberTwoModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractAlchemistCard) card).magicNumberTwo;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractAlchemistCard) card).baseMagicNumberTwo;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractAlchemistCard) card).isMagicNumberTwoUpgraded;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        ((AbstractAlchemistCard) card).isMagicNumberTwoModified = v;
    }
}
