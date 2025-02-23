package com.cron.alchemistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.util.CheckCombat;
import com.cron.alchemistmod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Vapours extends AbstractAlchemistRelic {
    private static final RelicTier RARITY = RelicTier.BOSS;

    public static final String ID = AlchemistMod.makeID(Vapours.class.getSimpleName());
    public static final RelicStrings RELIC_STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final Texture IMG = TextureLoader.getTexture(AlchemistMod.makeRelicTexturePath(Vapours.class.getSimpleName() + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(AlchemistMod.makeRelicOutlinePath(Vapours.class.getSimpleName() + ".png"));
    public static final Logger logger = LogManager.getLogger(Vapours.class.getSimpleName());

    public Vapours() {
        super(ID, IMG, OUTLINE, RARITY, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return RELIC_STRINGS.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster++;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster--;
    }

    @Override
    public void atBattleStart() {
        if (CheckCombat.isEliteCombat() || CheckCombat.isBossCombat()) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                AbstractPotion potion = returnRandomPotion();

                switch (potion.ID) {
                    case AncientPotion.POTION_ID:
                        AbstractDungeon.actionManager.addToBottom(
                                new ApplyPowerAction(monster, AbstractDungeon.player, new ArtifactPower(monster, potion.getPotency()), potion.getPotency())
                        );
                        break;
                    case BlockPotion.POTION_ID:
                        AbstractDungeon.actionManager.addToBottom(
                                new GainBlockAction(monster, potion.getPotency())
                        );
                        break;
                    case CultistPotion.POTION_ID:
                        AbstractDungeon.actionManager.addToBottom(
                                new ApplyPowerAction(monster, AbstractDungeon.player, new RitualPower(monster, potion.getPotency(), false), potion.getPotency())
                        );
                        break;
                    case DexterityPotion.POTION_ID:
                        AbstractDungeon.actionManager.addToBottom(
                                new ApplyPowerAction(monster, AbstractDungeon.player, new DexterityPower(monster, potion.getPotency()), potion.getPotency())
                        );
                        break;
                    case EssenceOfSteel.POTION_ID:
                        AbstractDungeon.actionManager.addToBottom(
                                new ApplyPowerAction(monster, AbstractDungeon.player, new PlatedArmorPower(monster, potion.getPotency()), potion.getPotency())
                        );
                        break;
                    case SteroidPotion.POTION_ID:
                        AbstractDungeon.actionManager.addToBottom(
                                new ApplyPowerAction(monster, AbstractDungeon.player, new StrengthPower(monster, potion.getPotency()), potion.getPotency())
                        );
                        AbstractDungeon.actionManager.addToBottom(
                                new ApplyPowerAction(monster, AbstractDungeon.player, new LoseStrengthPower(monster, potion.getPotency()), potion.getPotency())
                        );
                        break;
                    case FruitJuice.POTION_ID:
                        monster.increaseMaxHp(potion.getPotency(), true);
                        break;
                    case GhostInAJar.POTION_ID:
                        AbstractDungeon.actionManager.addToBottom(
                                new ApplyPowerAction(monster, AbstractDungeon.player, new IntangiblePower(monster, potion.getPotency()), potion.getPotency())
                        );
                        break;
                    case HeartOfIron.POTION_ID:
                        AbstractDungeon.actionManager.addToBottom(
                                new ApplyPowerAction(monster, AbstractDungeon.player, new MetallicizePower(monster, potion.getPotency()), potion.getPotency())
                        );
                        break;
                    case LiquidBronze.POTION_ID:
                        AbstractDungeon.actionManager.addToBottom(
                                new ApplyPowerAction(monster, AbstractDungeon.player, new ThornsPower(monster, potion.getPotency()), potion.getPotency())
                        );
                        break;
                    case RegenPotion.POTION_ID:
                        AbstractDungeon.actionManager.addToBottom(
                                new ApplyPowerAction(monster, AbstractDungeon.player, new RegenPower(monster, potion.getPotency()), potion.getPotency())
                        );
                        break;
                    case SpeedPotion.POTION_ID:
                        AbstractDungeon.actionManager.addToBottom(
                                new ApplyPowerAction(monster, AbstractDungeon.player, new DexterityPower(monster, potion.getPotency()), potion.getPotency())
                        );
                        AbstractDungeon.actionManager.addToBottom(
                                new ApplyPowerAction(monster, AbstractDungeon.player, new LoseDexterityPower(monster, potion.getPotency()), potion.getPotency())
                        );
                        break;
                    case StrengthPotion.POTION_ID:
                        AbstractDungeon.actionManager.addToBottom(
                                new ApplyPowerAction(monster, AbstractDungeon.player, new StrengthPower(monster, potion.getPotency()), potion.getPotency())
                        );
                        break;
                    default:
                        break;
                }

                logger.info("queued the potion effect");
            }
        }
    }

    private static AbstractPotion returnRandomPotion() {
        int roll = AbstractDungeon.potionRng.random(0, 99);
        if (roll < PotionHelper.POTION_COMMON_CHANCE) {
            return returnRandomPotion(AbstractPotion.PotionRarity.COMMON);
        } else if (roll < PotionHelper.POTION_UNCOMMON_CHANCE + PotionHelper.POTION_COMMON_CHANCE) {
            return returnRandomPotion(AbstractPotion.PotionRarity.UNCOMMON);
        } else {
            return returnRandomPotion(AbstractPotion.PotionRarity.RARE);
        }
    }

    private static AbstractPotion returnRandomPotion(AbstractPotion.PotionRarity rarity) {
        ArrayList<AbstractPotion> potions = new ArrayList<>();
        potions.add(new AncientPotion());
        potions.add(new BlockPotion());
        potions.add(new CultistPotion());
        potions.add(new DexterityPotion());
        potions.add(new EssenceOfSteel());
        potions.add(new SteroidPotion());
        potions.add(new FruitJuice());
        potions.add(new LiquidBronze());
        potions.add(new RegenPotion());
        potions.add(new SpeedPotion());
        potions.add(new StrengthPotion());
        if (AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.IRONCLAD) {
            potions.add(new HeartOfIron());
        } else if (AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.THE_SILENT) {
            potions.add(new GhostInAJar());
        }

        AbstractPotion potion = potions.get((AbstractDungeon.potionRng.random(potions.size() - 1)));

        while (potion.rarity != rarity) {
            potion = potions.get((AbstractDungeon.potionRng.random(potions.size() - 1)));
        }

        return potion;
    }

    public AbstractRelic makeCopy() {
        return new Vapours();
    }
}

