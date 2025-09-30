package com.cron.alchemistmod.commands;

import basemod.devcommands.ConsoleCommand;
import com.cron.alchemistmod.potions.BottledPotion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;

public class BottleCommand extends ConsoleCommand {

    public BottleCommand() {
        this.requiresPlayer = true;
        this.simpleCheck = false; // Enable advanced parsing for autocomplete
        this.minExtraTokens = 1;
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        if (tokens.length < 2) {
            System.out.println("Usage: bottle [card_id] [upgrade_count]");
            return;
        }

        // Parse card ID
        String cardId = tokens[1];
        AbstractCard card = CardLibrary.getCard(cardId);

        if (card == null) {
            System.out.println("Card not found: " + cardId);
            return;
        }

        // Parse upgrade count (default to 0 if not provided)
        int upgradeCount = 0;
        if (tokens.length > 2) {
            try {
                upgradeCount = Integer.parseInt(tokens[2]);
                if (upgradeCount < 0) {
                    System.out.println("Upgrade count must be >= 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid upgrade count. Must be a number >= 0.");
                return;
            }
        }

        // Upgrade the card the specified number of times
        for (int i = 0; i < upgradeCount; i++) {
            if (card.canUpgrade()) {
                card.upgrade();
            } else {
                System.out.println("Card cannot be upgraded further: " + card.name);
                break;
            }
        }

        // Create and add the potion
        BottledPotion potion = new BottledPotion(card);
        AbstractDungeon.player.obtainPotion(potion);
        System.out.println("Bottled: " + card.name + (upgradeCount > 0 ? " (+" + upgradeCount + ")" : ""));
    }

    // AUTOCOMPLETE IMPLEMENTATION
    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> options = new ArrayList<>();

        if (depth == 1) { // Autocomplete for card ID
            String input = tokens[depth].toLowerCase();
            for (AbstractCard card : CardLibrary.getAllCards()) {
                String id = card.cardID;
                if (id.toLowerCase().startsWith(input)) {
                    options.add(id);
                }
            }
        } else if (depth == 2) { // Autocomplete for upgrade count
            options.add("0"); // Default (not upgraded)
            options.add("1"); // Single upgrade
            options.add("5"); // Example for multi-upgrade cards like Searing Blow
            options.add("10"); // Example for multi-upgrade cards like Searing Blow
        }

        return options;
    }

    @Override
    public void errorMsg() {
        System.out.println("Invalid usage. Try:");
        System.out.println("bottle [card_id] [upgrade_count]");
    }
}