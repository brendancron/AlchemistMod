package com.cron.alchemistmod.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.cards.Defend;
import com.cron.alchemistmod.cards.ElementalBurst;
import com.cron.alchemistmod.cards.Strike;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

public class TheAlchemist extends CustomPlayer {
    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_ALCHEMIST;
        @SpireEnum(name = "DEFAULT_GRAY_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_GRAY;
        @SpireEnum(name = "DEFAULT_GRAY_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int HAND_SIZE = 5;
    public static final int ORB_SLOTS = 0;

    private static final String[] NAMES = new String[] {
            "The Alchemist",
            "the Alchemist"
    };

    private static final String[] TEXT = new String[] {
            "This is text",
            "More text",
            "Even more text yo"
    };

    public static final String MY_CHARACTER_SHOULDER_2 = AlchemistMod.makeGeneralPath("shoulder2.png"); // campfire pose
    public static final String MY_CHARACTER_SHOULDER_1 = AlchemistMod.makeGeneralPath("shoulder.png"); // another campfire pose
    public static final String MY_CHARACTER_CORPSE = AlchemistMod.makeGeneralPath("corpse.png"); // dead corpse
//    public static final String MY_CHARACTER_SKELETON_ATLAS = "img/char/skeleton.atlas"; // spine animation atlas
//    public static final String MY_CHARACTER_SKELETON_JSON = "img/char/skeleton.json"; // spine animation json
    public static final String[] orbTextures = {
            AlchemistMod.makeEnergyOrbPath("cover.png"),
            AlchemistMod.makeEnergyOrbPath("layer1.png"),
            AlchemistMod.makeEnergyOrbPath("layer2.png"),
            AlchemistMod.makeEnergyOrbPath("layer3.png"),
            AlchemistMod.makeEnergyOrbPath("layer4.png"),
            AlchemistMod.makeEnergyOrbPath("layer5.png"),
            AlchemistMod.makeEnergyOrbPath("layer1d.png"),
            AlchemistMod.makeEnergyOrbPath("layer2d.png"),
            AlchemistMod.makeEnergyOrbPath("layer3d.png"),
            AlchemistMod.makeEnergyOrbPath("layer4d.png"),
            AlchemistMod.makeEnergyOrbPath("layer5d.png")
    };

    public TheAlchemist() {
        super(NAMES[0], Enums.THE_ALCHEMIST, orbTextures,AlchemistMod.makeEnergyOrbPath("vfx.png"), null,
                new SpriterAnimation(AlchemistMod.makeAnimationPath("default.scml")));


        // =============== TEXTURES, ENERGY, LOADOUT =================

        initializeClass(null, // required call to load textures and setup energy/loadout.
                // I left these in DefaultMod.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                MY_CHARACTER_SHOULDER_2, // campfire pose
                MY_CHARACTER_SHOULDER_1, // another campfire pose
                MY_CHARACTER_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================


        // =============== ANIMATIONS =================

//        loadAnimation(
//                THE_DEFAULT_SKELETON_ATLAS,
//                THE_DEFAULT_SKELETON_JSON,
//                1.0f);
//        AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
//        e.setTime(e.getEndTime() * MathUtils.random());

        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(ElementalBurst.ID);

        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Potion Belt");
        UnlockTracker.markRelicAsSeen("Potion Belt");
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() { // the rest of the character loadout so includes your character select screen info plus hp and starting gold
        return new CharSelectInfo(NAMES[0], "My character is a person from the outer worlds. He makes magic stuff happen.",
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, HAND_SIZE,
                this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.COLOR_GRAY;
    }

    @Override
    public Color getCardRenderColor() {
        return AlchemistMod.DEFAULT_GRAY;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike();
    }

    @Override
    public Color getCardTrailColor() {
        return AlchemistMod.DEFAULT_GRAY;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {

    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheAlchemist();
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return AlchemistMod.DEFAULT_GRAY;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }
}