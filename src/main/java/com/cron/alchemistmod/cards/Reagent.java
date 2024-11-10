package com.cron.alchemistmod.cards;

import basemod.abstracts.CustomCard;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.powers.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Reagent extends CustomCard {
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAlchemist.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;

    public final static String ID = AlchemistMod.makeID(Reagent.class.getSimpleName());
    public static final String NAME = CardCrawlGame.languagePack.getCardStrings(ID).NAME;
    public static final String DESCRIPTION = CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = CardCrawlGame.languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = AlchemistMod.makeCardPath(Reagent.class.getSimpleName() + ".png");

    public Reagent() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(AirElement.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new AirElement(p, p, 1), 1)
            );
        } else if (p.hasPower(DarkElement.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new DarkElement(p, p, 1), 1)
            );
        } else if (p.hasPower(EarthElement.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new EarthElement(p, p, 1), 1)
            );
        } else if (p.hasPower(FireElement.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new FireElement(p, p, 1), 1)
            );
        } else if (p.hasPower(LightElement.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new LightElement(p, p, 1), 1)
            );
        } else if (p.hasPower(MagicElement.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new MagicElement(p, p, 1), 1)
            );
        } else if (p.hasPower(WaterElement.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new WaterElement(p, p, 1), 1)
            );
        }
    }
}
