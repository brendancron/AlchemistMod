package com.cron.alchemistmod.actions;

import com.cron.alchemistmod.powers.EarthElement;
import com.cron.alchemistmod.powers.FireElement;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class ShiftingSandAction extends AbstractGameAction {
    private final DamageInfo info;
    private final int magicNumber;

    public ShiftingSandAction(AbstractCreature target, DamageInfo info, int magicNumber) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
        this.magicNumber = magicNumber;
    }

    public void update() {
        if (this.shouldCancelAction()) {
            this.isDone = true;
        } else {
            this.tickDuration();
            if (this.isDone) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_HEAVY, false));
                int oldBlock = this.target.currentBlock;
                this.target.damage(this.info);
                if (oldBlock > this.target.currentBlock) {
                    AbstractDungeon.actionManager.addToBottom(
                            new ApplyPowerAction(AbstractDungeon.player, this.source, new EarthElement(AbstractDungeon.player, this.source, this.magicNumber), this.magicNumber)
                    );
                }
                if (this.target.lastDamageTaken > 0) {
                    AbstractDungeon.actionManager.addToBottom(
                            new ApplyPowerAction(AbstractDungeon.player, this.source, new FireElement(AbstractDungeon.player, this.source, this.magicNumber), this.magicNumber)
                    );
                }

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                } else {
                    this.addToTop(new WaitAction(0.1F));
                }
            }
        }
    }
}
