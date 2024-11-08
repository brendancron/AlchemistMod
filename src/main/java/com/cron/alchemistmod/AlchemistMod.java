package com.cron.alchemistmod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.cron.alchemistmod.cards.Strike;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.cron.alchemistmod.util.IDCheckDontTouchPls;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@SpireInitializer
public class AlchemistMod implements EditCardsSubscriber, EditStringsSubscriber, EditCharactersSubscriber {

    public static final Logger logger = LogManager.getLogger(AlchemistMod.class.getName());
    private static String modID;

    private static final String BUTTON = "images/select/button.png";
    private static final String PORTRAIT = "images/select/portrait.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "images/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "images/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "images/corpse.png";

    public static final Color DEFAULT_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);
    private static final String ATTACK_DEFAULT_GRAY = "images/cardback/bg_attack.png";
    private static final String SKILL_DEFAULT_GRAY = "images/cardback/bg_skill.png";
    private static final String POWER_DEFAULT_GRAY = "images/cardback/bg_power.png";

    private static final String ENERGY_ORB_DEFAULT_GRAY = "images/cardback/energy_orb.png";
    private static final String CARD_ENERGY_ORB = "images/cardback/small_orb.png";

    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "images/cardback/bg_attack_p.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "images/cardback/bg_skill_p.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "images/cardback/bg_power_p.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "images/cardback/energy_orb_p.png";


    public AlchemistMod() {
        BaseMod.subscribe(this);
        setModID("AlchemistMod");
        BaseMod.addColor(TheAlchemist.Enums.COLOR_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
    }

    public static void initialize() {
        new AlchemistMod();
    }

    @Override
    public void receiveEditStrings() {
        // Load custom card strings
        BaseMod.loadCustomStringsFile(CardStrings.class, "localization/eng/CardStrings.json");

//        // CardStrings
//        BaseMod.loadCustomStringsFile(CardStrings.class,
//                getModID() + "localization/eng/CardStrings.json");
//
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                "localization/eng/PowerStrings.json");
//
//        // RelicStrings
//        BaseMod.loadCustomStringsFile(RelicStrings.class,
//                getModID() + "Resources/localization/eng/RelicStrings.json");
//
//        // Event Strings
//        BaseMod.loadCustomStringsFile(EventStrings.class,
//                getModID() + "Resources/localization/eng/EventStrings.json");
//
//        // PotionStrings
//        BaseMod.loadCustomStringsFile(PotionStrings.class,
//                getModID() + "Resources/localization/eng/PotionStrings.json");
//
//        // CharacterStrings
//        BaseMod.loadCustomStringsFile(CharacterStrings.class,
//                getModID() + "Resources/localization/eng/CharacterStrings.json");
//
//        // OrbStrings
//        BaseMod.loadCustomStringsFile(OrbStrings.class,
//                getModID() + "Resources/localization/eng/OrbStrings.json");
    }

    @Override
    public void receiveEditCards() {
        // Register the custom card
        new AutoAdd(getModID()) // ${project.artifactId}
                .packageFilter(Strike.class) // filters to any class in the same package as AbstractDefaultCard, nested packages included
                .setDefaultSeen(true)
                .cards();
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheAlchemist(), BUTTON, PORTRAIT, TheAlchemist.Enums.THE_ALCHEMIST);
    }

    // mod id

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

    public static String makeCardPath(String resourcePath) {
        return "images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return "images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return "images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return "images/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return "images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return "images/events/" + resourcePath;
    }
}
