package com.cron.alchemistmod.cards;

import com.badlogic.gdx.math.MathUtils;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Arrays;

public class ShadowControl extends AbstractAlchemistCard {
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheAlchemist.Enums.COLOR_GRAY;

    private static final int COST = 3;
    private static final int DAMAGE = 5;
    private static final int MAGIC = 4;
    private static final int MAGIC_UPGRADE = 2;


    public final static String ID = AlchemistMod.makeID(ShadowControl.class.getSimpleName());
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = AlchemistMod.makeCardPath(ShadowControl.class.getSimpleName() + ".png");

    public ShadowControl() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = MAGIC;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(MAGIC_UPGRADE);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }

    @Override
    public void applyPowers() {
        this.applyPowersToBlock();

        ArrayList<AbstractMonster> monsterList = AbstractDungeon.getCurrRoom().monsters.monsters;
        float[] tempDamageList = new float[monsterList.size()];

        Arrays.fill(tempDamageList, (float) this.baseDamage);

        for(int i = 0; i < tempDamageList.length; i++) {
            AbstractMonster monster = monsterList.get(i);
            for (AbstractRelic relic : AbstractDungeon.player.relics) {
                tempDamageList[i] = relic.atDamageModify(tempDamageList[i], this);
            }

            for (AbstractPower power : monster.powers) {
                tempDamageList[i] = power.atDamageGive(tempDamageList[i], this.damageTypeForTurn, this);
            }

            for (AbstractPower power : monster.powers) {
                tempDamageList[i] = power.atDamageFinalGive(tempDamageList[i], this.damageTypeForTurn, this);
            }

            if (tempDamageList[i] < 0.0F) {
                tempDamageList[i] = 0.0F;
            }
        }

        this.multiDamage = new int[tempDamageList.length];
        for(int i = 0; i < tempDamageList.length; i++) {
            this.multiDamage[i] = MathUtils.floor(tempDamageList[i]);
        }

        this.damage = this.multiDamage[0];
        for (int tempDamage : this.multiDamage) {
            this.damage = Math.min(this.damage, tempDamage);
        }

        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster monster) {
        this.applyPowersToBlock();
        this.isDamageModified = false;
        if (monster != null) {
            float tempDamage = (float) this.baseDamage;

            for (AbstractRelic relic : AbstractDungeon.player.relics) {
                tempDamage = relic.atDamageModify(tempDamage, this);
            }

            for (AbstractPower power : monster.powers) {
                tempDamage = power.atDamageGive(tempDamage, this.damageTypeForTurn, this);
            }

            for (AbstractPower power : monster.powers) {
                tempDamage = power.atDamageReceive(tempDamage, this.damageTypeForTurn, this);
            }

            for (AbstractPower power : monster.powers) {
                tempDamage = power.atDamageFinalGive(tempDamage, this.damageTypeForTurn, this);
            }

            for (AbstractPower power : monster.powers) {
                tempDamage = power.atDamageFinalReceive(tempDamage, this.damageTypeForTurn, this);
            }

            if (tempDamage < 0.0F) {
                tempDamage = 0.0F;
            }

            this.damage = MathUtils.floor(tempDamage);

            this.isDamageModified = this.damage != this.baseDamage;
        }
    }
}
