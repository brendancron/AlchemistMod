package com.cron.alchemistmod.util;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Texture;
import com.cron.alchemistmod.AlchemistMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckboxHelper {
    public static final ModPanel panel = new ModPanel();
    static float xPos = 350.0f;
    static float yPos = 750.0f;
    public static final Logger logger = LogManager.getLogger(CheckboxHelper.class.getSimpleName());

    public static void createPanel() {
        // Create the settings panel
        BaseMod.registerModBadge(new Texture(AlchemistMod.makePowerPath("PoisonedPotionsPower32.png")), AlchemistMod.getModID(), "Author Name", "Description of mod", panel);

        panel.addUIElement(createCheckbox("toyOrnathoperDisabled"));
        panel.addUIElement(createCheckbox("sacredBarkDisabled"));
        panel.addUIElement(createCheckbox("potionBeltDisabled"));
        panel.addUIElement(createCheckbox("liquidMemoriesNerfed"));
        panel.addUIElement(createCheckbox("toyOrnathoperNerfed"));
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
}
