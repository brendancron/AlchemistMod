package com.cron.alchemistmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.potions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlindBrewAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(BlindBrewAction.class.getSimpleName());

    public BlindBrewAction() {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        AbstractPotion potion = limitedRandomPotion();
        potion.use(AbstractDungeon.player);

        this.isDone = true;
    }

    public AbstractPotion limitedRandomPotion() {
        int roll = AbstractDungeon.potionRng.random(0, 99);
        AbstractPotion.PotionRarity rarity;
        if (roll < PotionHelper.POTION_COMMON_CHANCE) {
            rarity = AbstractPotion.PotionRarity.COMMON;
        } else if (roll < PotionHelper.POTION_UNCOMMON_CHANCE + PotionHelper.POTION_COMMON_CHANCE) {
            rarity = AbstractPotion.PotionRarity.UNCOMMON;
        } else {
            rarity = AbstractPotion.PotionRarity.RARE;
        }

        AbstractPotion potion = PotionHelper.getRandomPotion();
        boolean badPotion = true;
        while (badPotion) {
            logger.info(potion.ID);
            if (
                    !(potion instanceof FairyPotion) &&
                    !(potion instanceof BlessingOfTheForge && AbstractDungeon.player.hand.isEmpty()) &&
                    !(potion instanceof RegenPotion) &&
                    !(potion instanceof SmokeBomb) &&
                    !(potion instanceof BloodPotion) &&
                    potion.rarity == rarity
            ) {
                badPotion = false;
            } else {
                potion = PotionHelper.getRandomPotion();
            }
        }

        return potion;
    }
}
