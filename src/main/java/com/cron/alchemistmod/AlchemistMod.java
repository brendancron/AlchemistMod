package com.cron.alchemistmod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.cron.alchemistmod.cards.Strike;
import com.cron.alchemistmod.characters.TheAlchemist;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;

@SpireInitializer
public class AlchemistMod implements EditCardsSubscriber, EditStringsSubscriber, EditCharactersSubscriber {

    public static final String MOD_ID = "AlchemistMod";

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
    }

    @Override
    public void receiveEditCards() {
        // Register the custom card
        new AutoAdd(MOD_ID) // ${project.artifactId}
                .packageFilter(Strike.class) // filters to any class in the same package as AbstractDefaultCard, nested packages included
                .setDefaultSeen(true)
                .cards();
    }

    public static String makeID(String idText) {
        return MOD_ID + ":" + idText;
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheAlchemist(), BUTTON, PORTRAIT, TheAlchemist.Enums.THE_ALCHEMIST);
    }
}
