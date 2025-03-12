package com.cron.alchemistmod.events;

import com.cron.alchemistmod.potions.BottledPotion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class MasterAlchemistEvent extends AbstractImageEvent {
    public static final String ID = "MasterAlchemistEvent";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private int screenNum = 0;

    public MasterAlchemistEvent() {
        super(NAME, DESCRIPTIONS[0], "img/events/cardDonation.jpg"); // Replace with your event image
        this.imageEventText.setDialogOption(OPTIONS[0]); // "Donate a Card"
        this.imageEventText.setDialogOption(OPTIONS[1]); // "Leave"
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (screenNum) {
            case 0: // Initial screen
                switch (buttonPressed) {
                    case 0: // "Donate a Card"
                        // Open the card selection screen
                        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck, 1, OPTIONS[2], false, false, false, true);
                        screenNum = 1; // Move to the next screen
                        break;
                    case 1: // "Leave"
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // "You leave without a word."
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]); // "Continue"
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 2; // Move to the end screen
                        break;
                }
                break;
            case 1: // Card selection screen
                if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    AbstractCard donatedCard = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

                    // Remove the card from the player's deck
                    AbstractDungeon.player.masterDeck.removeCard(donatedCard);

                    // Show the card being removed
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(donatedCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));

                    // Create and add the bottled version
                    BottledPotion potion = new BottledPotion(donatedCard);
                    AbstractDungeon.player.obtainPotion(potion);

                    // Update the event text
                    this.imageEventText.updateBodyText(DESCRIPTIONS[2]); // "Admiring the master alchemist's craftsmanship..."
                    this.imageEventText.updateDialogOption(0, OPTIONS[4]); // "Continue"
                    this.imageEventText.clearRemainingOptions();
                    screenNum = 2; // Move to the end screen
                }
                break;
            case 2: // End screen
                openMap(); // End the event
                break;
        }
    }
}
