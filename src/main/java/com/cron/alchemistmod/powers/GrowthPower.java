package com.cron.alchemistmod.powers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.Element;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.potions.FruitJuice;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class GrowthPower extends AbstractAlchemistPower {
    public static final String POWER_ID = AlchemistMod.makeID(GrowthPower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static int IdOffset = 0;
    private final ArrayList<Element> elementsLeft;

    private static final Texture tex84 = TextureLoader.getTexture(AlchemistMod.makePowerPath(GrowthPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(AlchemistMod.makePowerPath(GrowthPower.class.getSimpleName() + "32.png"));

    public GrowthPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = POWER_STRINGS.NAME;
        ID = POWER_ID + IdOffset++;

        this.elementsLeft = new ArrayList<>();
        this.elementsLeft.add(Element.AIR);
        this.elementsLeft.add(Element.DARK);
        this.elementsLeft.add(Element.EARTH);
        this.elementsLeft.add(Element.FIRE);
        this.elementsLeft.add(Element.LIGHT);
        this.elementsLeft.add(Element.MAGIC);
        this.elementsLeft.add(Element.WATER);

        this.owner = owner;
        this.amount = elementsLeft.size();
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public GrowthPower(final AbstractCreature owner, final AbstractCreature source) {
        this(owner, source, 1);
    }

    @Override
    public void onGainElement(AbstractElement element) {
        this.elementsLeft.remove(element.element);
        this.amount = this.elementsLeft.size();

        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(new FruitJuice()));
            AbstractDungeon.actionManager.addToBottom(
                    new RemoveSpecificPowerAction(owner, source, this)
            );
        }

        this.updateDescription();
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

        description = description.concat(POWER_STRINGS.DESCRIPTIONS[3]);
    }

    public String elementDescription(Element element) {
        if (element == Element.AIR) {
            return POWER_STRINGS.DESCRIPTIONS[4];
        } else if (element == Element.DARK) {
            return POWER_STRINGS.DESCRIPTIONS[5];
        } else if (element == Element.EARTH) {
            return POWER_STRINGS.DESCRIPTIONS[6];
        } else if (element == Element.FIRE) {
            return POWER_STRINGS.DESCRIPTIONS[7];
        } else if (element == Element.LIGHT) {
            return POWER_STRINGS.DESCRIPTIONS[8];
        } else if (element == Element.MAGIC) {
            return POWER_STRINGS.DESCRIPTIONS[9];
        } else if (element == Element.WATER) {
            return POWER_STRINGS.DESCRIPTIONS[10];
        } else {
            return null;
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new GrowthPower(owner, source, amount);
    }

    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new GrowthPower(owner, source, amount);
    }
}
