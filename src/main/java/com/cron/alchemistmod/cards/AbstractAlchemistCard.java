package com.cron.alchemistmod.cards;

import basemod.abstracts.CustomCard;
import com.cron.alchemistmod.powers.AbstractElement;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public abstract class AbstractAlchemistCard extends CustomCard {
    public AbstractAlchemistCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public AbstractAlchemistCard(String id, String name, RegionName img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public void triggerOnGainElement(AbstractElement element) {
    }

    public void triggerOnObtainPotion(AbstractPotion potion) {
    }

    public void triggerOnDestroyPotion() {
    }

    public void triggerOnRemovePotion(AbstractPotion potion) {
    }
}
