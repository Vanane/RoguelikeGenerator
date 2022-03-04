package plugins.roguelike.entities.behaviours;

import plugins.roguelike.entities.creatures.Creature;

public abstract class Behaviour {
    protected Creature creature;

    public Behaviour(Creature c)
    {
        creature = c;
    }

    public abstract void update();
}
