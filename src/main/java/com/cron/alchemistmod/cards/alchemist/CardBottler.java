package com.cron.alchemistmod.cards.alchemist;

import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.actions.PurgeCardAction;
import com.cron.alchemistmod.actions.SelectAndPayForCardAction;
import com.cron.alchemistmod.cards.AbstractAlchemistCard;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.potions.BottledPotion;
import com.cron.alchemistmod.util.PotionUtils;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.core.Settings;

public class CardBottler extends AbstractAlchemistCard {
    public static final String ID = AlchemistMod.makeID(CardBottler.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = AlchemistMod.makeAlchemistCardPath(Caffeine.class.getSimpleName() + ".png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAlchemist.Enums.ALCHEMIST;

    private static final int COST = 1;
    private static final int MAGIC = 1;

    public CardBottler() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.selfRetain = true;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean hasFreeSlot = PotionUtils.hasFreeSlot(p);
        if (!hasFreeSlot) {
            cantUseMessage = "No empty potion slots!";
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean hasFreeSlot = PotionUtils.hasFreeSlot(p);
        if (!hasFreeSlot) {
            AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, "No empty potion slots!", true));
            return;
        }

        AbstractDungeon.actionManager.addToBottom(
            new SelectAndPayForCardAction(p, (chosenCard) -> {
                AbstractDungeon.actionManager.addToBottom(
                    new PurgeCardAction(p, chosenCard)
                );
                AbstractPotion bottledPotion = new BottledPotion(chosenCard);
                AbstractDungeon.actionManager.addToBottom(
                    new ObtainPotionAction(bottledPotion)
                );
            })
        );
    }

}
