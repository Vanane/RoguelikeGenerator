package plugins.terminal_ui;

import java.awt.Rectangle;

import com.pitchounous.roguelike.ui.BasicUI;
import com.pitchounous.roguelike.world.World;

import asciiPanel.AsciiPanel;

public class Interface extends BasicUI {

	private AsciiPanel terminal;
	private AsciiCamera camera;

	private final int SCREEN_WIDTH = 80;
	private final int SCREEN_HEIGHT = 60;

	public Interface(World world) {
		super(world);

		Rectangle mapDimensions = new Rectangle(world.width, world.height);
		Rectangle gameViewArea = new Rectangle(SCREEN_WIDTH, SCREEN_HEIGHT - 5);
		terminal = new AsciiPanel(SCREEN_WIDTH, SCREEN_HEIGHT);
		camera = new AsciiCamera(mapDimensions, gameViewArea);

		add(terminal);
		setSize(SCREEN_WIDTH * 9, SCREEN_HEIGHT * 16);
		repaint();
	}

	public AsciiPanel getTerminal() {
		return terminal;
	}

	public void render() {
		camera.lookAt(terminal, world, world.getPlayer().getX(), world.getPlayer().getY());
		terminal.repaint();
	}

	public void renderGameOver() {
		System.out.println("GAME OVER - interface");
	}

	@Override
	public void start() {
		final double FRAMES_PER_SECOND = 60;
		final long TIME_PER_LOOP = (long) (1000000000 / FRAMES_PER_SECOND);
		long startTime;
		long endTime;
		long sleepTime;

		isRunning = true;
		while (isRunning) {

			startTime = System.nanoTime();
			processInput();
			update();
			render();
			endTime = System.nanoTime();

			sleepTime = TIME_PER_LOOP - (endTime - startTime);
			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime / 1000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			// Waiting after showing the GAME OVER screen and before exiting
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	public void update() {
		try {
			world.update();
		} catch (Error e) {
			// Game is over
			isRunning = false;
		}
	}

	@Override
	protected void processInput() {
		world.processInput(kl.getLastInput());
	};
}