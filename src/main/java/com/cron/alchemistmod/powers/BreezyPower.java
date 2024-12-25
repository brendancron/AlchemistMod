package com.cron.alchemistmod.powers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BreezyPower extends AbstractAlchemistPower {
    public AbstractCreature source;

    public static final String POWER_ID = AlchemistMod.makeID(BreezyPower.class.getSimpleName());
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final Texture tex84 = TextureLoader.getTexture(AlchemistMod.makePowerPath(BreezyPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(AlchemistMod.makePowerPath(BreezyPower.class.getSimpleName() + "32.png"));

    public BreezyPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = POWER_STRINGS.NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        for (int i = 0; i < AbstractDungeon.player.hand.group.size() && i < this.amount; i++) {
            AbstractDungeon.actionManager.addToBottom(
                    new DiscardSpecificCardAction(AbstractDungeon.player.hand.group.get(i))
            );
        }

        AbstractDungeon.actionManager.addToBottom(
                new DrawCardAction(this.amount)
        );
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            description = POWER_STRINGS.DESCRIPTIONS[0];
        } else {
            description = POWER_STRINGS.DESCRIPTIONS[1] + amount + POWER_STRINGS.DESCRIPTIONS[2] + amount + POWER_STRINGS.DESCRIPTIONS[3];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new BreezyPower(owner, source, amount);
    }

    @Override
    public AbstractAlchemistPower makeCopy(int amount) {
        return new BreezyPower(owner, source, amount);
    }
}
