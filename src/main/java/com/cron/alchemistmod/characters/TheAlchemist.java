package com.cron.alchemistmod.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.cron.alchemistmod.AlchemistMod;
import com.cron.alchemistmod.cards.alchemist.*;
import com.cron.alchemistmod.relics.PotionBag;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;
import java.util.List;

public class TheAlchemist extends CustomPlayer {
    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_ALCHEMIST;
        @SpireEnum(name = "ALCHEMIST") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor ALCHEMIST;
        @SpireEnum(name = "ALCHEMIST") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int HAND_SIZE = 5;
    public static final int ORB_SLOTS = 0;
    private static final String ID = AlchemistMod.makeID(TheAlchemist.class.getSimpleName());

    private static final String[] NAMES = CardCrawlGame.languagePack.getCharacterString(ID).NAMES;

    private static final String[] TEXT = CardCrawlGame.languagePack.getCharacterString(ID).TEXT;
    public static final String MY_CHARACTER_IDLE = AlchemistMod.makeCharacterPath("idle.png");

    public static final String MY_CHARACTER_SHOULDER_2 = AlchemistMod.makeCharacterPath("shoulder.png"); // campfire pose
    public static final String MY_CHARACTER_SHOULDER_1 = AlchemistMod.makeCharacterPath("shoulder.png"); // another campfire pose
    public static final String MY_CHARACTER_CORPSE = AlchemistMod.makeCharacterPath("corpse.png"); // dead corpse
//    public static final String MY_CHARACTER_SKELETON_ATLAS = "img/char/skeleton.atlas"; // spine animation atlas
//    public static final String MY_CHARACTER_SKELETON_JSON = "img/char/skeleton.json"; // spine animation json

    public static final String[] orbTextures = {
            AlchemistMod.makeEnergyOrbPath("alchemist/cover.png"),
            AlchemistMod.makeEnergyOrbPath("alchemist/layer1.png"),
            AlchemistMod.makeEnergyOrbPath("alchemist/layer2.png"),
            AlchemistMod.makeEnergyOrbPath("alchemist/layer3.png"),
            AlchemistMod.makeEnergyOrbPath("alchemist/layer4.png"),
            AlchemistMod.makeEnergyOrbPath("alchemist/layer5.png"),
            AlchemistMod.makeEnergyOrbPath("alchemist/layer1d.png"),
            AlchemistMod.makeEnergyOrbPath("alchemist/layer2d.png"),
            AlchemistMod.makeEnergyOrbPath("alchemist/layer3d.png"),
            AlchemistMod.makeEnergyOrbPath("alchemist/layer4d.png"),
            AlchemistMod.makeEnergyOrbPath("alchemist/layer5d.png")
    };

    public TheAlchemist() {
        super(NAMES[0], Enums.THE_ALCHEMIST, orbTextures,AlchemistMod.makeEnergyOrbPath("alchemist/vfx.png"), new float[]{0,0,0,0,0,0,0,0,0,0,0},
                new SpriterAnimation(AlchemistMod.makeAnimationPath("default.scml")));


        // =============== TEXTURES, ENERGY, LOADOUT =================

        initializeClass(MY_CHARACTER_IDLE, // required call to load textures and setup energy/loadout.
                // I left these in DefaultMod.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                MY_CHARACTER_SHOULDER_2, // campfire pose - not currently right size
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
        retVal.add(EarthStrike.ID);
        retVal.add(FireStrike.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(AirDefend.ID);
        retVal.add(WaterDefend.ID);

        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(PotionBag.ID);
        UnlockTracker.markRelicAsSeen(PotionBag.ID);
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() { // the rest of the character loadout so includes your character select screen info plus hp and starting gold
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, HAND_SIZE,
                this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.ALCHEMIST;
    }

    @Override
    public Color getCardRenderColor() {
        return AlchemistMod.ALCHEMIST_COLOR;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike();
    }

    @Override
    public Color getCardTrailColor() {
        return AlchemistMod.ALCHEMIST_COLOR;
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
        return AlchemistMod.ALCHEMIST_COLOR;
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

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel(AlchemistMod.makeGeneralPath("scenes/alchemist1.png"), "ATTACK_HEAVY"));
        panels.add(new CutscenePanel(AlchemistMod.makeGeneralPath("scenes/alchemist2.png")));
        panels.add(new CutscenePanel(AlchemistMod.makeGeneralPath("scenes/alchemist3.png")));
        return panels;
    }
}