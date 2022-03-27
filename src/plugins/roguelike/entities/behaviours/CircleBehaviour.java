package plugins.roguelike.entities.behaviours;

import java.time.Instant;

import plugins.roguelike.entities.creatures.Creature;

public class CircleBehaviour extends Behaviour {
    /** Contient le timestamp du dernier mouvement fait par la cr�ature */
    private long lastMoveTime;
    private int nextX, nextY;

    /** Temps en millisecondes entre chaque pas */
    private long timeBetweenMoves = 500;

    public CircleBehaviour(Creature c) {
        super(c);
        this.lastMoveTime = 0;
        this.nextX = 1;
        this.nextY = 1;
    }

    @Override
    public void update() {
        if (Instant.now().toEpochMilli() > lastMoveTime + timeBetweenMoves) {
            lastMoveTime = Instant.now().toEpochMilli();
            creature.move(nextX, nextY);
            setNextDirection();
        }
    }

    private void setNextDirection() {
        nextX = -nextX;
        nextY = -nextY;
    }
}
