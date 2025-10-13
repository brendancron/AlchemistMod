package com.cron.alchemistmod.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.powers.AbstractElement;
import com.cron.alchemistmod.util.ExhaustDecision;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public abstract class AbstractAlchemistCard extends CustomCard {
    public boolean isMagicNumberTwoModified;
    public int magicNumberTwo;
    public int baseMagicNumberTwo;
    public boolean isMagicNumberTwoUpgraded;

    public AbstractAlchemistCard(String id, String name, Class<?> clazz, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(
            id,
            name,
            AlchemistMod.makeCardPath(clazz, color),
            cost,
            rawDescription,
            type,
            color,
            rarity,
            target);
    }

    @Override
    public void loadCardImage(String img) {
        String resolvedImg = img;

        // Check if the main image exists
        if (img == null || !Gdx.files.internal(img).exists()) {
            System.out.println("[AlchemistMod] Missing card image: " + img);

            // Choose a fallback based on card type
            String fallbackBase = "TheAlchemistResources/images/cards/_Undefined";
            switch (this.type) {
                case SKILL:
                    resolvedImg = fallbackBase + "Skill.png";
                    break;
                case POWER:
                    resolvedImg = fallbackBase + "Power.png";
                    break;
                case ATTACK:
                case STATUS:
                case CURSE:
                default:
                    resolvedImg = fallbackBase + "Attack.png";
                    break;
            }

            // Optional log
            System.out.println("[AlchemistMod] Using fallback image: " + resolvedImg);
        }

        // Delegate back to base implementation (keeps caching + beta handling)
        super.loadCardImage(resolvedImg);
    }

    public AbstractAlchemistCard(String id, String name, RegionName img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public void triggerOnGainElement(AbstractElement element) {
    }

    public void triggerOnObtainPotion(AbstractPotion potion) {
    }

    public void triggerOnUsePotion(AbstractPotion potion) {
    }

    public void triggerOnDiscardPotion(AbstractPotion potion) {
    }

    public void triggerOnBattleStart() {
    }

    public ExhaustDecision onTryExhaust() {
        return ExhaustDecision.ALLOW;
    }
}
