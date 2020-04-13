package Models.Actions;

import Models.Creatures.AbstractCreature;
import Models.Object.AbstractPower;

public class FightActions {
    public static void attack(AbstractCreature from, AbstractCreature to, int amount) {
        for (AbstractPower p : from.powers) {
            amount = p.onAttack(amount);
        }

        for (AbstractPower p : to.powers) {
            amount = p.onDamage(amount);
        }

        to.changeBlock(-amount);
        if (to.getBlock() < 0) {
            to.changeHealth(to.getBlock());
            to.changeBlock(-to.getBlock());
        }
    }

    public static void block(AbstractCreature to, int amount) {
        for (AbstractPower p : to.powers) {
            amount = p.onBlock(amount);
        }

        to.changeBlock(amount);
    }
}
