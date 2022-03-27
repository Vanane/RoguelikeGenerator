package plugins.roguelike.entities.behaviours;

import plugins.roguelike.entities.creatures.Creature;

public class EmptyBehaviour extends Behaviour {
    public EmptyBehaviour(Creature c) {
        super(c);
    }

    @Override
    public void update() {
        // Does nothing, no behaviour
    }

}
