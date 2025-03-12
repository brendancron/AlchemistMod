package com.cron.alchemistmod.potions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class BottledPotion extends AbstractPotion {
    private static final String POTION_ID = "TheAlchemist:BottledPotion";

    private final AbstractCard bottledCard;

    public BottledPotion(AbstractCard card) {
        super(getPotionName(card), POTION_ID, PotionRarity.PLACEHOLDER, PotionSize.BOTTLE, PotionColor.SMOKE);
        bottledCard = card.makeStatEquivalentCopy();

        this.targetRequired = (bottledCard.target == AbstractCard.CardTarget.ENEMY);
        this.potency = getPotency();
        this.description = "When played, use " + card.name + ".";
        this.tips.add(new PowerTip(getPotionName(card), description));
        String fixedDescription = card.rawDescription
                .replace("!D!", String.valueOf(card.baseDamage))
                .replace("!B!", String.valueOf(card.baseBlock))
                .replace("!M!", String.valueOf(card.baseMagicNumber));

        this.tips.add(new PowerTip(card.name, fixedDescription));
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractCard cardCopy = bottledCard.makeStatEquivalentCopy();
        cardCopy.purgeOnUse = true;
        cardCopy.freeToPlayOnce = true;

        cardCopy.applyPowers();

        switch (bottledCard.target) {
            case ENEMY:
                if (target instanceof AbstractMonster) {
                    cardCopy.use(AbstractDungeon.player, (AbstractMonster) target);
                }
                break;
            case ALL_ENEMY:
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (!m.isDeadOrEscaped()) {
                        cardCopy.use(AbstractDungeon.player, m);
                    }
                }
                break;
            case SELF:
            case NONE:
                cardCopy.use(AbstractDungeon.player, null);
                break;
            default:
                cardCopy.use(AbstractDungeon.player, null);
        }
    }

    @Override
    public int getPotency(int i) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BottledPotion(bottledCard);
    }

    public static String getPotionName(AbstractCard card) {
        return "Bottled " + card.name;
    }
}
