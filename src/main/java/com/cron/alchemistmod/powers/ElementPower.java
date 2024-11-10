package com.cron.alchemistmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class ElementPower extends AbstractPower implements CloneablePowerInterface {
    public abstract ElementPower makeCopy(int amount);

    public static boolean hasElement(AbstractPlayer player) {
        if (player.hasPower(AirElement.POWER_ID)) {
            return true;
        } else if (player.hasPower(DarkElement.POWER_ID)) {
            return true;
        } else if (player.hasPower(EarthElement.POWER_ID)) {
            return true;
        } else if (player.hasPower(FireElement.POWER_ID)) {
            return true;
        } else if (player.hasPower(LightElement.POWER_ID)) {
            return true;
        } else if (player.hasPower(MagicElement.POWER_ID)) {
            return true;
        } else if (player.hasPower(WaterElement.POWER_ID)) {
            return true;
        } else {
            return false;
        }
    }

    public static ElementPower getElement(AbstractPlayer player) {
        if (player.hasPower(AirElement.POWER_ID)) {
            return (ElementPower) player.getPower(AirElement.POWER_ID);
        } else if (player.hasPower(DarkElement.POWER_ID)) {
            return (ElementPower) player.getPower(DarkElement.POWER_ID);
        } else if (player.hasPower(EarthElement.POWER_ID)) {
            return (ElementPower) player.getPower(EarthElement.POWER_ID);
        } else if (player.hasPower(FireElement.POWER_ID)) {
            return (ElementPower) player.getPower(FireElement.POWER_ID);
        } else if (player.hasPower(LightElement.POWER_ID)) {
            return (ElementPower) player.getPower(LightElement.POWER_ID);
        } else if (player.hasPower(MagicElement.POWER_ID)) {
            return (ElementPower) player.getPower(MagicElement.POWER_ID);
        } else if (player.hasPower(WaterElement.POWER_ID)) {
            return (ElementPower) player.getPower(WaterElement.POWER_ID);
        }

        return null;
    }
}
