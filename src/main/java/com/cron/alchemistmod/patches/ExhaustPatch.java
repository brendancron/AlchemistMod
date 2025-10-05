package com.cron.alchemistmod.patches;

import basemod.ReflectionHacks;
import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.cron.alchemistmod.util.ExhaustDecision;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static com.cron.alchemistmod.util.ExhaustDecision.*;

@SpirePatch(
        clz = UseCardAction.class,
        method = "update"
)
public class ExhaustPatch {
    @SpirePrefixPatch
    public static SpireReturn<Void> Prefix(UseCardAction __instance) {
        AbstractCard card = ReflectionHacks.getPrivate(__instance, UseCardAction.class, "targetCard");

        if (!(card instanceof AbstractAlchemistCard)) {
            return SpireReturn.Continue();
        }

        AbstractAlchemistCard alch = (AbstractAlchemistCard) card;
        ExhaustDecision decision = alch.onTryExhaust();

        if (decision == ExhaustDecision.ALLOW) {
            return SpireReturn.Continue();
        }

        __instance.exhaustCard = false;

        switch (decision) {
            case CANCEL:
                AbstractDungeon.player.hand.moveToDiscardPile(card);
                break;
            default:
                // safety: do nothing special
                break;
        }

        card.exhaustOnUseOnce = false;
        card.dontTriggerOnUseCard = false;
        AbstractDungeon.player.cardInUse = null;
        __instance.isDone = true;
        AbstractDungeon.player.hand.applyPowers();
        AbstractDungeon.player.hand.glowCheck();

        return SpireReturn.Return(null);

    }
}
