package com.cron.alchemistmod.util;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Texture;
import com.cron.alchemistmod.AlchemistMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.relics.PotionBelt;
import com.megacrit.cardcrawl.relics.SacredBark;
import com.megacrit.cardcrawl.relics.ToyOrnithopter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class SettingsPanel {
    public static final ModPanel panel = new ModPanel();
    static float xPos = 350.0f;
    static float yPos = 750.0f;
    static int textIndex = 0;
    public static final Logger logger = LogManager.getLogger(SettingsPanel.class.getSimpleName());

    public static void createPanel() {
        // Create the settings panel
        BaseMod.registerModBadge(new Texture(AlchemistMod.makePowerPath("PoisonedPotionsPower32.png")), AlchemistMod.getModID(), "Author Name", "Description of mod", panel);

        panel.addUIElement(createLabel());

        panel.addUIElement(createCheckbox("toyOrnathoperDisabled"));
        panel.addUIElement(createCheckbox("sacredBarkDisabled"));
        panel.addUIElement(createCheckbox("potionBeltDisabled"));

        panel.addUIElement(createLabel());

        panel.addUIElement(createCheckbox("liquidMemoriesNerfed"));
    }

    public static ModLabeledToggleButton createCheckbox(String key) {
        ModLabeledToggleButton toggleButton = new ModLabeledToggleButton(
                CardCrawlGame.languagePack.getUIString(AlchemistMod.makeID(key)).TEXT[0],
                xPos, yPos,
                Settings.CREAM_COLOR, FontHelper.tipHeaderFont,
                MyModConfig.getBool(key, false), panel,
                (modLabel -> {}),
                (button) -> MyModConfig.setBool(key, button.enabled)
        );

        yPos -= 50;

        return toggleButton;
    }

    public static ModLabel createLabel() {
        ModLabel label = new ModLabel(
                CardCrawlGame.languagePack.getUIString(AlchemistMod.makeID(SettingsPanel.class.getSimpleName())).TEXT[textIndex++],
                xPos, yPos,
                Settings.CREAM_COLOR, FontHelper.tipHeaderFont,
                panel,
                (modLabel -> {})
        );

        yPos -= 60;

        return label;
    }

    public static void disableRelics() {
        logger.info("Common relics avalible: " + AbstractDungeon.commonRelicPool.size());
        logger.info("disabling relics");

        if (MyModConfig.getBool("toyOrnathoperDisabled", false)) {
            AbstractDungeon.commonRelicPool.removeIf((relic) -> Objects.equals(relic, ToyOrnithopter.ID));
        }
        if (MyModConfig.getBool("sacredBarkDisabled", false)) {
            AbstractDungeon.bossRelicPool.removeIf((relic) -> Objects.equals(relic, SacredBark.ID));
        }
        if (MyModConfig.getBool("potionBeltDisabled", false)) {
            AbstractDungeon.commonRelicPool.removeIf((relic) -> Objects.equals(relic, PotionBelt.ID));
        }

        logger.info("relics disabled");
        logger.info("Common relics avalible: " + AbstractDungeon.commonRelicPool.size());
    }
}
