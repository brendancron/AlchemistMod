package com.cron.alchemistmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;

import java.util.ArrayList;
import java.util.ListIterator;

public class RemovePotionAction extends AbstractGameAction {
    private final boolean first;
    public final AbstractPlayer player;
    public final int amount;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;


    public RemovePotionAction(AbstractPlayer player, boolean first, int amount) {
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.first = first;
        this.player = player;
        this.amount = amount;
    }

    public RemovePotionAction(AbstractPlayer player, boolean first) {
        this(player, first, 1);
    }

    public RemovePotionAction(AbstractPlayer player) {
        this(player, true);
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST) {
            ArrayList<AbstractPotion> list = null;

            if(first) {
                list = getFirstPotions();
            } else {
                list = getLastPotions();
            }

            if (list != null) {
                for(AbstractPotion potion: list) {
                    this.player.removePotion(potion);
                }
            }
        }

        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("AbstractPotion");
        TEXT = uiStrings.TEXT;
    }

    public ArrayList<AbstractPotion> getFirstPotions() {
        ArrayList<AbstractPotion> list = new ArrayList<>();
        ListIterator<AbstractPotion> var2 = this.player.potions.listIterator();

        while(var2.hasNext() && list.size() < this.amount) {
            AbstractPotion p = var2.next();
            if (!(p instanceof PotionSlot)) {
                list.add(p);
            }
        }

        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    public ArrayList<AbstractPotion> getLastPotions() {
        ArrayList<AbstractPotion> list = new ArrayList<>();
        ListIterator<AbstractPotion> var2 = this.player.potions.listIterator(this.player.potions.size());

        while(var2.hasPrevious() && list.size() < this.amount) {
            AbstractPotion p = var2.previous();
            if (!(p instanceof PotionSlot)) {
                list.add(p);
            }
        }

        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }
}

