package com.cron.alchemistmod.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class RandomCardGenerator {

    public static ArrayList<AbstractCard> generateCards(int num) {
        ArrayList<AbstractCard> choices = new ArrayList<>();
        while(choices.size() < num) {
            boolean isDuplicate = false;
            AbstractCard tmp = AbstractDungeon.returnTrulyRandomCardInCombat();
            for(AbstractCard c : choices) {
                if (c.cardID.equals(tmp.cardID)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                choices.add(tmp.makeCopy());
            }
        }
        return choices;
    }

}
