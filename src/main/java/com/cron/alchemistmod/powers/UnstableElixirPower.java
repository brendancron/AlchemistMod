package com.cron.alchemistmod.powers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.Element;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class UnstableElixirPower extends AbstractAlchemistPower {
    public static final String POWER_ID = AlchemistMod.makeID(UnstableElixirPower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static int IdOffset = 0;
    private final ArrayList<Element> elementsLeft;
    private final int damage;

    private static final Texture tex84 = TextureLoader.getTexture(AlchemistMod.makePowerPath(UnstableElixirPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(AlchemistMod.makePowerPath(UnstableElixirPower.class.getSimpleName() + "32.png"));

    public UnstableElixirPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = POWER_STRINGS.NAME;
        ID = POWER_ID + IdOffset++;

        this.elementsLeft = new ArrayList<>();
        this.elementsLeft.add(Element.AIR);
        this.elementsLeft.add(Element.EARTH);
        this.elementsLeft.add(Element.FIRE);
        this.elementsLeft.add(Element.WATER);

        this.owner = owner;
        this.amount = elementsLeft.size();
        this.damage = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onGainElement(AbstractElement element) {
        this.elementsLeft.remove(element.element);
        this.amount = this.elementsLeft.size();

        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAllEnemiesAction(this.source, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE)
            );
            AbstractDungeon.actionManager.addToBottom(
                    new RemoveSpecificPowerAction(owner, source, this)
            );
        }

        this.updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(
                new RemoveSpecificPowerAction(this.owner, this.owner, this)
        );
    }

    @Override
    public void updateDescription() {
        description = POWER_STRINGS.DESCRIPTIONS[0];

        for (int i = 0; i < elementsLeft.size() - 1; i++) {
            Element element = elementsLeft.get(i);
            description = description.concat(elementDescription(element));
            description = description.concat(POWER_STRINGS.DESCRIPTIONS[1]);
        }

        if (elementsLeft.size() >= 2) {
            description = description.concat(POWER_STRINGS.DESCRIPTIONS[2]);
            Element element = elementsLeft.get(elementsLeft.size() - 1);
            description = description.concat(elementDescription(element));
        } else if (elementsLeft.size() == 1) {
            Element element = elementsLeft.get(0);
            description = description.concat(elementDescription(element));
        }

        description = description.concat(POWER_STRINGS.DESCRIPTIONS[3] + this.damage + POWER_STRINGS.DESCRIPTIONS[4]);
    }

    public String elementDescription(Element element) {
        if (element == Element.AIR) {
            return POWER_STRINGS.DESCRIPTIONS[5];
        } else if (element == Element.EARTH) {
            return POWER_STRINGS.DESCRIPTIONS[6];
        } else if (element == Element.FIRE) {
            return POWER_STRINGS.DESCRIPTIONS[7];
        } else if (element == Element.WATER) {
            return POWER_STRINGS.DESCRIPTIONS[8];
        } else {
            return null;
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new UnstableElixirPower(owner, source, amount);
    }

    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new UnstableElixirPower(owner, source, amount);
    }
}
