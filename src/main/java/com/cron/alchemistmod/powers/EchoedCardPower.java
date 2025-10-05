package com.cron.alchemistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.actions.PlayStoredCardAction;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EchoedCardPower extends AbstractAlchemistPower {
    public static final String POWER_ID = AlchemistMod.makeID(EchoedCardPower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final Texture tex84 = TextureLoader.getTexture(AlchemistMod.makePowerPath(DredgesPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(AlchemistMod.makePowerPath(DredgesPower.class.getSimpleName() + "32.png"));

    private final AbstractCard echoedCard;

    private static int instanceCounter = 0;

    public EchoedCardPower(final AbstractCreature owner, final AbstractCreature source, final int amount, AbstractCard echoedCard) {
        this.name = POWER_STRINGS.NAME + ": " + echoedCard.name;

        this.ID = POWER_ID + instanceCounter;
        instanceCounter++;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.echoedCard = echoedCard;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = String.format(
                    POWER_STRINGS.DESCRIPTIONS[0],
                    this.echoedCard.name
            );
        } else {
            this.description = String.format(
                    POWER_STRINGS.DESCRIPTIONS[1],
                    this.echoedCard.name,
                    this.amount
            );
        }
    }

    @Override
    public void atStartOfTurn() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new PlayStoredCardAction(echoedCard));
        amount--;

        updateDescription();

        if (amount <= 0) {
            AbstractDungeon.actionManager.addToBottom(
                new RemoveSpecificPowerAction(owner, owner, this)
            );
        }
    }

    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new EchoedCardPower(owner, source, amount, echoedCard);
    }

    @Override
    public AbstractPower makeCopy() {
        return new EchoedCardPower(owner, source, amount, echoedCard);
    }
}
