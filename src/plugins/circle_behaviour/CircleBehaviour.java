package plugins.circle_behaviour;

import java.time.Instant;

import plugins.roguelike.entities.behaviours.Behaviour;
import plugins.roguelike.entities.creatures.Creature;

public class CircleBehaviour extends Behaviour {
    int[][] moves = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
    int moveIndex = 0;

    /** Time in millisecond between two moves */
    long lastMoveTime = 0;
    long timeBetweenMoves = 500;

    public CircleBehaviour(Creature c) {
        super(c);
    }

    @Override
    public void update() {
        if (Instant.now().toEpochMilli() > lastMoveTime + timeBetweenMoves) {
            lastMoveTime = Instant.now().toEpochMilli();
            int[] move = this.moves[this.moveIndex];
            creature.move(move[0], move[1]);

            this.moveIndex = (this.moveIndex + 1) % 4;
        }
    }
}
