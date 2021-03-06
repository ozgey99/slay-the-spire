package Models.Cards.Red;

import Models.Cards.*;
import Models.Creatures.AbstractCharacter;
import Controller.Dungeon.Room.Fight;

public class ShrugItOff extends AbstractCard {

    public ShrugItOff(){
        name = "ShrugItOff";
        description = "Gain 8(11) block. Draw 1 card.";
        cost = 1;
        type = CardType.SKILL;
        color = CardColor.RED;
        rarity = CardRarity.COMMON;
        target = CardTarget.SELF;
        baseAttr = new BaseCardAttributes();
        baseAttr.block = 8;
        baseAttr.draw = 1;
        usable = true;
        upgradable = true;
    }

    @Override
    public boolean use(Fight f, AbstractCharacter player) {
        selected = false;
        if (!player.changeEnergy(-cost)) return false;

        f.getHand().addDeck(Deck.drawCard(f.getDraw(), f.getDiscard(), baseAttr.draw));

        player.changeBlock(baseAttr.block);
        return true;
    }

    @Override
    public void upgrade() {
        if (upgradable) {
            upgradable = false;
            this.baseAttr.block = 11;
            this.name = this.name + "+";

        }
    }

    @Override
    public AbstractCard makeCopy() {
        ShrugItOff copy = new ShrugItOff();
        copy.upgradable = this.upgradable;
        copy.baseAttr.block = this.baseAttr.block;
        return copy;
    }
}
