package com.cron.alchemistmod.potions;

import basemod.abstracts.CustomSavable;
import com.cron.alchemistmod.AlchemistMod;
import com.google.gson.JsonObject;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BottledPotion extends AbstractPotion implements CustomSavable {
    public static final String POTION_ID = AlchemistMod.makeID(BottledPotion.class.getSimpleName());;

    private static final Logger LOGGER = LogManager.getLogger(BottledPotion.class);

    private AbstractCard bottledCard;
    private boolean addBack;

    public BottledPotion() {
        super("Empty Bottled Potion", POTION_ID, PotionRarity.PLACEHOLDER, PotionSize.BOTTLE, PotionColor.SMOKE);
        this.potency = getPotency(0);
    }

    public BottledPotion(AbstractCard card, boolean addBack) {
        super(getPotionName(card), POTION_ID, PotionRarity.PLACEHOLDER, PotionSize.BOTTLE, PotionColor.SMOKE);
        this.potency = getPotency(0);
        initCard(card, addBack);
    }

    public void initCard(AbstractCard card, boolean addBack) {
        this.name = getPotionName(card);
        this.addBack = addBack;

        bottledCard = card.makeStatEquivalentCopy();

        this.targetRequired = (bottledCard.target == AbstractCard.CardTarget.ENEMY);

        this.description = "When played, use " + card.name + ".";
        this.tips.add(new PowerTip(getPotionName(card), description));
        String fixedDescription = card.rawDescription
                .replace("!D!", String.valueOf(card.baseDamage))
                .replace("!B!", String.valueOf(card.baseBlock))
                .replace("!M!", String.valueOf(card.baseMagicNumber));

        this.tips.add(new PowerTip(card.name, fixedDescription));
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractCard cardCopy = bottledCard.makeStatEquivalentCopy();
        cardCopy.applyPowers();

        switch (bottledCard.target) {
            case ENEMY:
                if (target instanceof AbstractMonster) {
                    cardCopy.use(AbstractDungeon.player, (AbstractMonster) target);
                }
                break;
            case ALL_ENEMY:
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (!m.isDeadOrEscaped()) {
                        cardCopy.use(AbstractDungeon.player, m);
                    }
                }
                break;
            case SELF:
            case NONE:
                cardCopy.use(AbstractDungeon.player, null);
                break;
            default:
                cardCopy.use(AbstractDungeon.player, null);
        }

        if (addBack) {
            // discard or exhaust it
            if (cardCopy.exhaustOnUseOnce || cardCopy.exhaust) {
                AbstractDungeon.player.exhaustPile.addToTop(cardCopy);
            } else {
                AbstractDungeon.player.discardPile.addToTop(cardCopy);
            }

            // Add the card back into your deck!
            AbstractCard cardToAdd = bottledCard.makeStatEquivalentCopy();
            cardToAdd.resetAttributes();
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(cardToAdd, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
        }
    }

    @Override
    public int getPotency(int i) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BottledPotion(bottledCard, false);
    }

    public static String getPotionName(AbstractCard card) {
        return "Bottled " + card.name;
    }

    @Override
    public Object onSave() {
        LOGGER.info("Saving Bottled Potion");
        JsonObject obj = new JsonObject();
        obj.addProperty("addBack", addBack);
        obj.addProperty("cardID", bottledCard.cardID);
        obj.addProperty("upgraded", bottledCard.upgraded);
        obj.addProperty("timesUpgraded", bottledCard.timesUpgraded);
        obj.addProperty("magicNumber", bottledCard.magicNumber);
        obj.addProperty("misc", bottledCard.misc);
        return obj;
    }

    @Override
    public void onLoad(Object o) {
        if(o instanceof JsonObject) {
            LOGGER.info("Loading Bottled Potion");
            JsonObject obj = ((JsonObject )o);
            boolean addBack = obj.get("addBack").getAsBoolean();
            String cardID = obj.get("cardID").getAsString();
            AbstractCard card = CardLibrary.getCard(cardID).makeCopy();
            card.upgraded = obj.get("upgraded").getAsBoolean();
            int timesUpgraded = obj.get("timesUpgraded").getAsInt();
            for (int i = 0; i < timesUpgraded; i++) {
                card.upgrade(); // This increments timesUpgraded AND applies stat changes
            }
            card.magicNumber = obj.get("magicNumber").getAsInt();
            card.misc = obj.get("misc").getAsInt();
            LOGGER.info("Card ID: {}, Upgraded {}, Times Upgraded {}, Magic {}, AddBack {}", cardID, card.upgraded, card.timesUpgraded, card.magicNumber, addBack);
            initCard(card, addBack);
        }
    }

    @Override
    public Class<?> savedType() {
        return JsonObject.class;
    }

}
