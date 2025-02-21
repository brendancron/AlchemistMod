package com.cron.alchemistmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class AbstractAlchemistRelic extends CustomRelic {

    public AbstractAlchemistRelic(String id, Texture texture, RelicTier tier, LandingSound sfx) {
        super(id, texture, tier, sfx);
    }

    public AbstractAlchemistRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) {
        super(id, texture, outline, tier, sfx);
    }

    public AbstractAlchemistRelic(String id, String imgName, RelicTier tier, LandingSound sfx) {
        super(id, imgName, tier, sfx);
    }

    public void onRemove() {
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
    }
}
