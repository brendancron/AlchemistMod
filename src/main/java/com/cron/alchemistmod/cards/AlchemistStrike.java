package com.cron.alchemistmod.cards;

import basemod.abstracts.CustomCard;
import com.cron.alchemistmod.AlchemistMod;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AlchemistStrike extends CustomCard {
    public final static String ID = AlchemistMod.makeID("AlchemistStrike");
    public static final String NAME = "Alchemist Strike";
    public static final String DESCRIPTION = "Deal !D! damage.";
    public static final String IMG_PATH = "images/cards/AlchemistStrike.png";

    private static final int COST = 1;
    private static final int DAMAGE = 10;
    private static final int DAMAGE_UPGRADE = 5;

    public AlchemistStrike() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.COMMON, CardTarget.ENEMY);
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

    @Override
    public AbstractCard makeCopy() {
        return new AlchemistStrike();
    }
}
