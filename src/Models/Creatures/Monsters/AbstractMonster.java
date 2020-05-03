package Models.Creatures.Monsters;
//import java.util.ArrayList;

import Models.Actions.FightActions;
import Models.Actions.PowerActions;
import Models.Creatures.AbstractCharacter;
import Models.Creatures.AbstractCreature;
import Models.Dungeon.Room.Fight;
import Models.Object.AbstractPower;

import java.util.ArrayList;

import static sts.Main.game;

public abstract class AbstractMonster extends AbstractCreature {
    String name;
    boolean selected = false;
    ArrayList<MonsterMove> moveList;

//    void addMove(AbstractPower monsterPower)
//    {
//        moveList.add(monsterPower);
//    }
//    void popMove(AbstractPower executedPower){ moveList.remove( executedPower); }

    public void act(Fight f, AbstractCharacter player) {
        System.out.println(name);
        MonsterMove next = getNextMoveAndRotate();
        FightActions.attack(this, player, next.getDamage());
        FightActions.block(next.owner, next.getBlock());

        for (AbstractPower p : next.powers) {
            PowerActions.addPower((next.isSelf) ? next.owner : player, p);
        }

        for(int r = 0; r < powers.size();r++)
        {
            System.out.println("MONSTER POWR IS " + powers.get(r).getName() + " " + powers.get(r).getAmount());

        }
    }

    public MonsterMove getNextMove() {
        return moveList.get(0);

    }

    public MonsterMove getNextMoveAndRotate() {
        MonsterMove m = moveList.get(0);
        moveList.remove(0);
        moveList.add(m);
        return m;
    }

    public String getName() {
        return name;
    }

    public boolean getSelected(){
        return selected;
    }

    public void setSelected(){
        selected = !selected;
    }
}