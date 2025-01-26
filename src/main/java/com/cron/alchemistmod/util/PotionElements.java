package com.cron.alchemistmod.util;

import com.megacrit.cardcrawl.potions.*;

import java.util.Objects;

public enum PotionElements {
    SWIFT_POTION (Element.AIR, Element.AIR, new SwiftPotion()),
    GAMBLERS_BREW (Element.AIR, Element.DARK, new GamblersBrew()),
    WEAK_POTION (Element.AIR, Element.EARTH, new WeakenPotion()),
    FEAR_POTION (Element.AIR, Element.FIRE, new FearPotion()),
    SNECKO_OIL (Element.AIR, Element.LIGHT, new SneckoOil()),
    DISTILLED_CHAOS (Element.AIR, Element.MAGIC, new DistilledChaosPotion()),
    LIQUID_MEMORIES (Element.AIR, Element.WATER, new LiquidMemories()),
    ENERGY_POTION (Element.DARK, Element.DARK, new EnergyPotion()),
    SPEED_POTION (Element.DARK, Element.EARTH, new SpeedPotion()),
    FLEX_POTION (Element.DARK, Element.FIRE, new SteroidPotion()),
    LIQUID_BRONZE (Element.DARK, Element.LIGHT, new LiquidBronze()),
    DUPLICATION_POTION (Element.DARK, Element.MAGIC, new DuplicationPotion()),
    POISON_POTION (Element.DARK, Element.WATER, new PoisonPotion()),
    BLOCK_POTION (Element.EARTH, Element.EARTH, new BlockPotion()),
    EXPLOSIVE_POTION (Element.EARTH, Element.FIRE, new ExplosivePotion()),
    DEXTERITY_POTION (Element.EARTH, Element.LIGHT, new DexterityPotion()),
    ESSENCE_OF_STEEL (Element.EARTH, Element.MAGIC, new EssenceOfSteel()),
    SKILL_POTION (Element.EARTH, Element.WATER, new SkillPotion()),
    FIRE_POTION (Element.FIRE, Element.FIRE, new FirePotion()),
    STRENGTH_POTION (Element.FIRE, Element.LIGHT, new StrengthPotion()),
    CULTIST_POTION (Element.FIRE, Element.MAGIC, new CultistPotion()),
    ATTACK_POTION (Element.FIRE, Element.WATER, new AttackPotion()),
    BLESSING_OF_THE_FORGE (Element.LIGHT, Element.LIGHT, new BlessingOfTheForge()),
    REGEN_POTION (Element.LIGHT, Element.MAGIC, new RegenPotion()),
    POWER_POTION (Element.LIGHT, Element.WATER, new PowerPotion()),
    ENTROPIC_BREW (Element.MAGIC, Element.MAGIC, new EntropicBrew()),
    ANCIENT_POTION (Element.MAGIC, Element.WATER, new AncientPotion()),
    COLORLESS_POTION (Element.WATER, Element.WATER, new ColorlessPotion());

    private final Element elementOne;
    private final Element elementTwo;
    private final AbstractPotion potion;

    PotionElements(Element elementOne, Element elementTwo, AbstractPotion potion) {
        this.elementOne = elementOne;
        this.elementTwo = elementTwo;
        this.potion = potion;
    }

    public static AbstractPotion getPotion (String elementOne, String elementTwo) {
        for(PotionElements potion : PotionElements.values()) {
            if (potion.elementOne.isElement(elementOne)) {
                if (potion.elementTwo.isElement(elementTwo)) {
                    return potion.potion;
                }
            } else {
                if (potion.elementTwo.isElement(elementOne)) {
                    if (potion.elementOne.isElement(elementTwo)) {
                        return potion.potion;
                    }
                }
            }
        }

        return null;
    }

    public static Element[] getElements(AbstractPotion potion) {
        for(PotionElements potionElements : PotionElements.values()) {
            if (Objects.equals(potionElements.potion.ID, potion.ID)) {
                return potionElements.getElements();
            }
        }

        return null;
    }

    public Element[] getElements() {
        Element[] elements = new Element[2];
        elements[0] = this.elementOne;
        elements[1] = this.elementTwo;
        return elements;
    }
}
