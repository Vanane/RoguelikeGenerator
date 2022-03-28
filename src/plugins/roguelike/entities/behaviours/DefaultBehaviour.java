package plugins.roguelike.entities.behaviours;

import java.util.Random;

import plugins.roguelike.entities.creatures.Creature;

public class DefaultBehaviour extends Behaviour {
	public DefaultBehaviour() {}
	
	public DefaultBehaviour(Creature c) {
		super(c);
	}

	@Override
	public void update() {
		Random rnd = new Random();
		int performAction = rnd.nextInt(100);
		if (performAction > 98) {

			// Moving on a cell with another creature on it make
			// this creature attacking the other
			int rndNr = rnd.nextInt(3);
			if (rndNr == 0) {
				creature.move(1, 0);
			} else if (rndNr == 1) {
				creature.move(-1, 0);
			} else if (rndNr == 2) {
				creature.move(0, 1);
			} else if (rndNr == 3) {
				creature.move(0, -1);
			}
		}

	}

}
