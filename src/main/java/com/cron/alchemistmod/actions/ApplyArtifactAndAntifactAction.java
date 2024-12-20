package com.cron.alchemistmod.actions;

import com.badlogic.gdx.Gdx;
import com.cron.alchemistmod.powers.AntifactPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerBuffEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerDebuffEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.Iterator;

public class ApplyArtifactAndAntifactAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private final ArtifactPower artifactPower;
    private final AntifactPower antifactPower;
    private float startingDuration;
    private static final Logger logger = LogManager.getLogger(ChooseAndTransformRandomCardAction.class.getName());

    public ApplyArtifactAndAntifactAction(AbstractCreature target, AbstractCreature source, ArtifactPower artifactPower, AntifactPower antifactPower, int stackAmount, boolean isFast, AbstractGameAction.AttackEffect effect) {
        if (Settings.FAST_MODE) {
            this.startingDuration = 0.1F;
        } else if (isFast) {
            this.startingDuration = Settings.ACTION_DUR_FASTER;
        } else {
            this.startingDuration = Settings.ACTION_DUR_FAST;
        }

        this.setValues(target, source, stackAmount);
        this.duration = this.startingDuration;
        this.artifactPower = artifactPower;
        this.antifactPower = antifactPower;

        this.actionType = ActionType.POWER;
        this.attackEffect = effect;
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.duration = 0.0F;
            this.startingDuration = 0.0F;
            this.isDone = true;
        }

    }

    public ApplyArtifactAndAntifactAction(AbstractCreature target, AbstractCreature source, ArtifactPower artifactPower, AntifactPower antifactPower, int stackAmount, boolean isFast) {
        this(target, source, artifactPower, antifactPower, stackAmount, isFast, AttackEffect.NONE);
    }

    public ApplyArtifactAndAntifactAction(AbstractCreature target, AbstractCreature source, ArtifactPower artifactPower, AntifactPower antifactPower) {
        this(target, source, artifactPower, antifactPower, artifactPower.amount);
    }

    public ApplyArtifactAndAntifactAction(AbstractCreature target, AbstractCreature source, ArtifactPower artifactPower, AntifactPower antifactPower, int stackAmount) {
        this(target, source, artifactPower, antifactPower, stackAmount, false, AttackEffect.NONE);
    }

    public ApplyArtifactAndAntifactAction(AbstractCreature target, AbstractCreature source, ArtifactPower artifactPower, AntifactPower antifactPower, int stackAmount, AbstractGameAction.AttackEffect effect) {
        this(target, source, artifactPower, antifactPower, stackAmount, false, effect);
    }

    public void update() {
        if (this.target != null && !this.target.isDeadOrEscaped()) {
            if (this.duration == this.startingDuration) {
                if (this.source != null) {
                    for (AbstractPower pow : this.source.powers) {
                        pow.onApplyPower(this.artifactPower, this.target, this.source);
                    }
                }

                if (this.target instanceof AbstractMonster && this.target.isDeadOrEscaped()) {
                    this.duration = 0.0F;
                    this.isDone = true;
                    return;
                }

                // test for exsisting artifact and antifact
                boolean canApplyArtifact = !this.target.hasPower(AntifactPower.POWER_ID);
                boolean canApplyAntifact = !this.target.hasPower("Artifact");

                if (canApplyArtifact) {
                    applyPower(this.artifactPower);
                } else {
                    this.addToTop(new TextAboveCreatureAction(this.target, TEXT[0]));
                    this.duration -= Gdx.graphics.getDeltaTime();
                    CardCrawlGame.sound.play("NULLIFY_SFX");
                    this.target.getPower(AntifactPower.POWER_ID).flashWithoutSound();
                    this.target.getPower(AntifactPower.POWER_ID).onSpecificTrigger();
                }

                if (canApplyAntifact) {
                    applyPower(this.antifactPower);
                } else {
                    this.addToTop(new TextAboveCreatureAction(this.target, TEXT[0]));
                    this.duration -= Gdx.graphics.getDeltaTime();
                    CardCrawlGame.sound.play("NULLIFY_SFX");
                    this.target.getPower("Artifact").flashWithoutSound();
                    this.target.getPower("Artifact").onSpecificTrigger();
                }
            }

            this.tickDuration();
        } else {
            this.isDone = true;
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ApplyPowerAction");
        TEXT = uiStrings.TEXT;
    }

    public void applyPower(AbstractPower powerToApply) {
        AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        boolean hasBuffAlready = false;
        Iterator<AbstractPower> var6 = this.target.powers.iterator();

        label148:
        while(true) {
            AbstractPower p;
            do {
                do {
                    if (!var6.hasNext()) {
                        if (powerToApply.type == PowerType.DEBUFF) {
                            this.target.useFastShakeAnimation(0.5F);
                        }

                        if (!hasBuffAlready) {
                            this.target.powers.add(powerToApply);
                            Collections.sort(this.target.powers);
                            powerToApply.onInitialApplication();
                            powerToApply.flash();
                            if (this.amount >= 0 || !powerToApply.ID.equals("Strength") && !powerToApply.ID.equals("Dexterity") && !powerToApply.ID.equals("Focus")) {
                                if (powerToApply.type == PowerType.BUFF) {
                                    AbstractDungeon.effectList.add(new PowerBuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, powerToApply.name));
                                } else {
                                    AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, powerToApply.name));
                                }
                            } else {
                                AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, powerToApply.name + TEXT[3]));
                            }

                            AbstractDungeon.onModifyPower();
                            if (this.target.isPlayer) {
                                int buffCount = 0;

                                for (AbstractPower power : this.target.powers) {
                                    if (power.type == PowerType.BUFF) {
                                        ++buffCount;
                                    }
                                }

                                if (buffCount >= 10) {
                                    UnlockTracker.unlockAchievement("POWERFUL");
                                }
                            }
                        }
                        break label148;
                    }

                    p = var6.next();
                } while(!p.ID.equals(powerToApply.ID));
            } while(p.ID.equals("Night Terror"));

            p.stackPower(this.amount);
            p.flash();
            if ((p instanceof StrengthPower || p instanceof DexterityPower) && this.amount <= 0) {
                AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, powerToApply.name + TEXT[3]));
            } else if (this.amount > 0) {
                if (p.type != PowerType.BUFF && !(p instanceof StrengthPower) && !(p instanceof DexterityPower)) {
                    AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, "+" + Integer.toString(this.amount) + " " + powerToApply.name));
                } else {
                    AbstractDungeon.effectList.add(new PowerBuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, "+" + Integer.toString(this.amount) + " " + powerToApply.name));
                }
            } else if (p.type == PowerType.BUFF) {
                AbstractDungeon.effectList.add(new PowerBuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, powerToApply.name + TEXT[3]));
            } else {
                AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, powerToApply.name + TEXT[3]));
            }

            p.updateDescription();
            hasBuffAlready = true;
            AbstractDungeon.onModifyPower();
        }
    }
}

