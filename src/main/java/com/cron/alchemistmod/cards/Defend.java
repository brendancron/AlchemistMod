package com.cron.alchemistmod.cards;

import basemod.abstracts.CustomCard;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.util.InsertSpacesIntoString;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Defend extends CustomCard {
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheAlchemist.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int BLOCK_UPGRADE = 3;

    public final static String ID = AlchemistMod.makeID(Defend.class.getSimpleName());
    public static final String NAME = InsertSpacesIntoString.insertSpacesIntoString(Defend.class.getSimpleName());
    public static final String DESCRIPTION = "Gain !B! Block.";
    public static final String IMG_PATH = "images/cards/AlchemistStrike.png";

    public Defend() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;

        this.tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(BLOCK_UPGRADE);

        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(p, p, block)
        );
    }
}
