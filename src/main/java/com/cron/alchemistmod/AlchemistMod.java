package com.cron.alchemistmod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.cron.alchemistmod.cards.alchemist.Strike;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.powers.AbstractAlchemistPower;
import com.cron.alchemistmod.powers.SacredFormPower;
import com.cron.alchemistmod.relics.PotionBag;
import com.cron.alchemistmod.util.IDCheckDontTouchPls;
import com.cron.alchemistmod.util.MagicNumber2;
import com.cron.alchemistmod.util.TrackPotions;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static basemod.BaseMod.addRelicToCustomPool;

@SpireInitializer
public class AlchemistMod implements
        EditCardsSubscriber,
        EditStringsSubscriber,
        EditCharactersSubscriber,
        EditRelicsSubscriber,
        PostBattleSubscriber,
        EditKeywordsSubscriber,
        PrePotionUseSubscriber,
        OnPlayerTurnStartSubscriber,
        PostPowerApplySubscriber
{

    public static final Logger logger = LogManager.getLogger("TheAlchemist");

    private static String modID;

    private static final String BUTTON = "TheAlchemistResources/images/select/button.png";
    private static final String PORTRAIT = "TheAlchemistResources/images/select/portrait.png";

    public static final Color ALCHEMIST_COLOR = CardHelper.getColor(255, 0, 0);
    private static final String ATTACK_ALCHEMIST = "TheAlchemistResources/images/cardback/alchemist/bg_attack.png";
    private static final String SKILL_ALCHEMIST = "TheAlchemistResources/images/cardback/alchemist/bg_skill.png";
    private static final String POWER_ALCHEMIST = "TheAlchemistResources/images/cardback/alchemist/bg_power.png";

    private static final String ENERGY_ORB_ALCHEMIST = "TheAlchemistResources/images/cardback/alchemist/energy_orb.png";
    private static final String CARD_ENERGY_ORB = "TheAlchemistResources/images/cardback/alchemist/small_orb.png";

    private static final String ATTACK_ALCHEMIST_PORTRAIT = "TheAlchemistResources/images/cardback/alchemist/bg_attack_p.png";
    private static final String SKILL_ALCHEMIST_PORTRAIT = "TheAlchemistResources/images/cardback/alchemist/bg_skill_p.png";
    private static final String POWER_ALCHEMIST_PORTRAIT = "TheAlchemistResources/images/cardback/alchemist/bg_power_p.png";
    private static final String ENERGY_ORB_ALCHEMIST_PORTRAIT = "TheAlchemistResources/images/cardback/alchemist/energy_orb_p.png";


    public AlchemistMod() {
        BaseMod.subscribe(this);
        setModID("TheAlchemist");
        BaseMod.addColor(TheAlchemist.Enums.ALCHEMIST, ALCHEMIST_COLOR, ALCHEMIST_COLOR, ALCHEMIST_COLOR,
                ALCHEMIST_COLOR, ALCHEMIST_COLOR, ALCHEMIST_COLOR, ALCHEMIST_COLOR,
                ATTACK_ALCHEMIST, SKILL_ALCHEMIST, POWER_ALCHEMIST, ENERGY_ORB_ALCHEMIST,
                ATTACK_ALCHEMIST_PORTRAIT, SKILL_ALCHEMIST_PORTRAIT, POWER_ALCHEMIST_PORTRAIT,
                ENERGY_ORB_ALCHEMIST_PORTRAIT, CARD_ENERGY_ORB);
    }

    public static void initialize() {
        new AlchemistMod();
    }

    // subscribers ----------------------------

    @Override
    public void receiveEditStrings() {
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/CardStrings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/PowerStrings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/RelicStrings.json");
//
//        // Event Strings
//        BaseMod.loadCustomStringsFile(EventStrings.class,
//                getModID() + "Resources/localization/eng/EventStrings.json");
//
//        // PotionStrings
//        BaseMod.loadCustomStringsFile(PotionStrings.class,
//                getModID() + "Resources/localization/eng/PotionStrings.json");
//
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/CharacterStrings.json");
//
//        // OrbStrings
//        BaseMod.loadCustomStringsFile(OrbStrings.class,
//                getModID() + "Resources/localization/eng/OrbStrings.json");
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new MagicNumber2());
        // Register the custom card
        new AutoAdd(AlchemistMod.class.getSimpleName()) // ${project.artifactId}
                .packageFilter(Strike.class) // filters to any class in the same package as AbstractDefaultCard, nested packages included
                .setDefaultSeen(true)
                .cards();
    }

    @Override
    public void receiveEditRelics() {
        addRelicToCustomPool(new PotionBag(), TheAlchemist.Enums.ALCHEMIST);
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheAlchemist(), BUTTON, PORTRAIT, TheAlchemist.Enums.THE_ALCHEMIST);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/KeywordStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        AbstractDungeon.player.powers.removeIf(power -> power instanceof SacredFormPower);
        TrackPotions.atCombatEnd();
    }
    @Override
    public void receivePrePotionUse(AbstractPotion abstractPotion) {
        TrackPotions.onPotionUsed(abstractPotion);
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        TrackPotions.atStartOfTurn();
    }

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            for (AbstractPower power : monster.powers) {
                if (power instanceof AbstractAlchemistPower) {
                    ((AbstractAlchemistPower) power).onAnyPowerApplied(abstractPower, abstractCreature, abstractCreature1);
                }
            }
        }

        AbstractPlayer player = AbstractDungeon.player;
        for (AbstractPower power : player.powers) {
            if (power instanceof AbstractAlchemistPower) {
                ((AbstractAlchemistPower) power).onAnyPowerApplied(abstractPower, abstractCreature, abstractCreature1);
            }
        }
    }

    // end subscribers ----------------------

    // mod id

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = AlchemistMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO

    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = AlchemistMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = AlchemistMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO

    // ====== YOU CAN EDIT AGAIN ======

    // paths

    public static String makeGeneralPath(String resourcePath) {
        return getModID() + "Resources/images/" + resourcePath;
    }

    public static String makeAnimationPath(String resourcePath) {
        return getModID() + "Resources/images/animation/" + resourcePath;
    }

    public static String makeAlchemistCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/alchemist/" + resourcePath;
    }

    public static String makeColorlessCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/colorless/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }

    public static String makeEnergyOrbPath(String resourcePath) {
        return getModID() + "Resources/images/energyOrb/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public static String makeCharacterPath(String resourcePath) {
        return getModID() + "Resources/images/character/theAlchemist/" + resourcePath;
    }

    public static String makeCardbackPath(String resourcePath) {
        return getModID() + "Resources/images/cardback/alchemist/" + resourcePath;
    }
}
