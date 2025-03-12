package com.cron.alchemistmod.events;

import com.badlogic.gdx.math.MathUtils;
import com.cron.alchemistmod.cards.alchemist.AirBurst;
import com.cron.alchemistmod.potions.BottledPotion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.shrines.Bonfire;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

public class MasterAlchemistEvent extends AbstractImageEvent {
    public static final String ID = "MasterAlchemistEvent";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private int screen = 0;
    private boolean cardSelect = false;

    public MasterAlchemistEvent() {
        super(NAME, DESCRIPTIONS[0], "img/events/cardDonation.jpg"); // Replace with your event image
        this.imageEventText.setDialogOption(OPTIONS[0]); // "Donate a Card"
        this.imageEventText.setDialogOption(OPTIONS[1]); // "Leave"

        this.noCardsInRewards = true;
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0) {
                            AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[3], false, false, false, true);
                            this.cardSelect = true;
                            this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        } else {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                            this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                            this.screen = 2;
                        }
                        break;
                    default:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);

                        if (AbstractDungeon.ascensionLevel >= 15) {
                            AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, MathUtils.ceil((float)AbstractDungeon.player.maxHealth * 0.05F), DamageInfo.DamageType.HP_LOSS));
                        }
                }

                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[4]);
                this.screen = 1;
                break;
            default:
                this.openMap();
        }
    }

    public void update() {
        super.update();
        if (this.cardSelect && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard card = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.remove(0);
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
            AbstractDungeon.player.masterDeck.removeCard(card);
            this.imageEventText.updateDialogOption(0, OPTIONS[1]);
            this.screen = 1;
            this.cardSelect = false;
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.getCurrRoom().rewards.clear();
            AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(new BottledPotion(card)));
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.combatRewardScreen.open();
        }

    }
}
