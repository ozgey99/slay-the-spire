package Models.Dungeon.Room;

import Models.Actions.RelicActions;
import Models.Creatures.AbstractCharacter;
import Models.Dungeon.AbstractRoom;
import Models.Main;
import Models.Object.AbstractRelic;
import Models.Object.Relics.Mango;
import Models.UI;

import java.util.ArrayList;

public class Treasure extends AbstractRoom {
    ArrayList<AbstractRelic> relics;
    int goldAmount;

    public Treasure(AbstractRoom c) {
        type = RoomType.CHEST;
        children = c;
        done = false;
        generate();
    }

    private void generate() {
        relics.add(new Mango());
        goldAmount = (int) (Math.random() * 100) + 10;
    }

    @Override
    public void start() {
        AbstractCharacter player = Main.game.getPlayer();

        while (!done) {
            int max = UI.displayTreasure(this);
            int in = UI.getInput(-1, max);
            if (in == -1) done = true;
            else if (in == relics.size()) {
                    player.changeGold(goldAmount);
                    goldAmount = 0;
            } else {
                    AbstractRelic r = relics.remove(in);
                    RelicActions.addRelic(player, r);
            }
        }
    }

    @Override
    public RoomType getType() {
        return null;
    }

    @Override
    public AbstractRoom getChildren() {
        return null;
    }

    @Override
    public boolean getDone() {
        return false;
    }

    public ArrayList<AbstractRelic> getRelics() {
        return relics;
    }

    public int getGoldAmount() {
        return goldAmount;
    }
}