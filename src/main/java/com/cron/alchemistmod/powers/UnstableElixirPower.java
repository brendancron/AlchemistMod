package com.cron.alchemistmod.powers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
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
import java.util.Objects;

public class UnstableElixirPower extends AbstractAlchemistPower {
    public static final String POWER_ID = AlchemistMod.makeID(UnstableElixirPower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static int IdOffset = 0;
    private final ArrayList<String> elementsLeft;
    private final int damage;

    private static final Texture tex84 = TextureLoader.getTexture(AlchemistMod.makePowerPath(UnstableElixirPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(AlchemistMod.makePowerPath(UnstableElixirPower.class.getSimpleName() + "32.png"));

    public UnstableElixirPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = POWER_STRINGS.NAME;
        ID = POWER_ID + IdOffset++;

        this.elementsLeft = new ArrayList<>();
        this.elementsLeft.add(AirElement.POWER_ID);
        this.elementsLeft.add(EarthElement.POWER_ID);
        this.elementsLeft.add(FireElement.POWER_ID);
        this.elementsLeft.add(WaterElement.POWER_ID);

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
        this.elementsLeft.remove(element.ID);
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
            String elementID = elementsLeft.get(i);
            description = description.concat(elementDescription(elementID));
            description = description.concat(POWER_STRINGS.DESCRIPTIONS[1]);
        }

        if (elementsLeft.size() >= 2) {
            description = description.concat(POWER_STRINGS.DESCRIPTIONS[2]);
            String elementID = elementsLeft.get(elementsLeft.size() - 1);
            description = description.concat(elementDescription(elementID));
        } else if (elementsLeft.size() == 1) {
            String elementID = elementsLeft.get(0);
            description = description.concat(elementDescription(elementID));
        }

        description = description.concat(POWER_STRINGS.DESCRIPTIONS[3] + this.damage + POWER_STRINGS.DESCRIPTIONS[4]);
    }

    public String elementDescription(String elementID) {
        if (Objects.equals(elementID, AirElement.POWER_ID)) {
            return POWER_STRINGS.DESCRIPTIONS[5];
        } else if (Objects.equals(elementID, EarthElement.POWER_ID)) {
            return POWER_STRINGS.DESCRIPTIONS[6];
        } else if (Objects.equals(elementID, FireElement.POWER_ID)) {
            return POWER_STRINGS.DESCRIPTIONS[7];
        } else if (Objects.equals(elementID, WaterElement.POWER_ID)) {
            return POWER_STRINGS.DESCRIPTIONS[8];
        }

        return null;
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
