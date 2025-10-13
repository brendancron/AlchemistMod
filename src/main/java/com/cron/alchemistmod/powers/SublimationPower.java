package com.cron.alchemistmod.powers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.Element;
import com.cron.alchemistmod.util.PotionElements;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class SublimationPower extends AbstractAlchemistPower {
    public static final String POWER_ID = AlchemistMod.makeID(SublimationPower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public SublimationPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = POWER_STRINGS.NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        configureTexture(SublimationPower.class);

        updateDescription();
    }

    @Override
    public void onDiscardPotion(AbstractPotion potion) {
        Element[] elements = PotionElements.getElements(potion);
        for (int i = 0; i < this.amount; i++) {
            int randomIndex = AbstractDungeon.miscRng.random(elements.length - 1);
            Element chosenElement = elements[randomIndex];
            AbstractDungeon.actionManager.addToBottom(
                    new MakeTempCardInDiscardAction(chosenElement.getCard(), 1)
            );
        }
    }

    @Override
    public void updateDescription() {
        description = POWER_STRINGS.DESCRIPTIONS[0] + amount + POWER_STRINGS.DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SublimationPower(owner, source, amount);
    }

    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new SublimationPower(owner, source, amount);
    }
}
