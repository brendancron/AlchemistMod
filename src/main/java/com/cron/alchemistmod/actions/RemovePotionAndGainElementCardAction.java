package com.cron.alchemistmod.actions;

import com.cron.alchemistmod.util.Element;
import com.cron.alchemistmod.util.PotionElements;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;

import java.util.ArrayList;
import java.util.ListIterator;

public class RemovePotionAndGainElementCardAction extends AbstractGameAction {
    private final boolean first;
    public final AbstractPlayer player;
    public final int amount;
    public final boolean doubleCards;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;


    public RemovePotionAndGainElementCardAction(AbstractPlayer player, boolean first, int amount, boolean doubleCards) {
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.first = first;
        this.player = player;
        this.amount = amount;
        this.doubleCards = doubleCards;
    }

    public RemovePotionAndGainElementCardAction(AbstractPlayer player, boolean first) {
        this(player, first, 1, false);
    }

    public RemovePotionAndGainElementCardAction(AbstractPlayer player) {
        this(player, false);
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST) {
            // remove potion
            ArrayList<AbstractPotion> list;

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

            // gain cards
            if (list != null) {
                for (AbstractPotion potion : list) {
                    Element[] elements = PotionElements.getElements(potion);
                    if (elements != null) {
                        for (Element element : elements) {
                            if (doubleCards) {
                                AbstractDungeon.actionManager.addToBottom(
                                        new MakeTempCardInHandAction(element.getCard(), 2, false)
                                );
                            } else {
                                AbstractDungeon.actionManager.addToBottom(
                                        new MakeTempCardInHandAction(element.getCard(), 1, false)
                                );
                            }
                        }
                    }
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

