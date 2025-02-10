package com.cron.alchemistmod.patches;

import com.cron.alchemistmod.relics.AbstractAlchemistRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "loseRelic"
)

public class LoseRelicPatch {
    @SpirePrefixPatch
    public static void loseRelic(AbstractPlayer __instance, String targetID) {
        AbstractRelic relic = __instance.getRelic(targetID);
        if (relic instanceof AbstractAlchemistRelic) {
            ((AbstractAlchemistRelic) relic).onRemove();
        }
    }
}

