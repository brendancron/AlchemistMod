package com.cron.alchemistmod.cards;

import basemod.abstracts.CustomCard;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.util.InsertSpacesIntoString;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RareAttack extends CustomCard {
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAlchemist.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int DAMAGE_UPGRADE = 3;

    public final static String ID = AlchemistMod.makeID(RareAttack.class.getSimpleName());
    public static final String NAME = InsertSpacesIntoString.insertSpacesIntoString(RareAttack.class.getSimpleName());
    public static final String DESCRIPTION = "Deal !D! damage.";
    public static final String IMG_PATH = "images/cards/AlchemistStrike.png";

    public RareAttack() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(DAMAGE_UPGRADE);  // Increase damage by 5 on upgrade, adjust as desired
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Create a DamageAction to deal damage to the target monster
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, this.baseDamage, DamageInfo.DamageType.NORMAL))
        );
    }
}
