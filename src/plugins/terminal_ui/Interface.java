package plugins.terminal_ui;

import java.awt.Rectangle;

import asciiPanel.AsciiPanel;
import plugins.roguelike.ui.BasicUI;
import plugins.roguelike.world.World;

/**
 * Terminal like interface using Ascii-panel library
 */
public class Interface extends BasicUI {

	private AsciiPanel terminal;
	private AsciiCamera camera;
	private Boolean closing;

	private final int SCREEN_WIDTH = 80;
	private final int SCREEN_HEIGHT = 60;

	/**
	 * Terminal UI plugin
	 *
	 * @param world
	 */
	public Interface(World world) {
		super(world);

		Rectangle mapDimensions = new Rectangle(world.width, world.height);
		Rectangle gameViewArea = new Rectangle(SCREEN_WIDTH, SCREEN_HEIGHT - 5);
		terminal = new AsciiPanel(SCREEN_WIDTH, SCREEN_HEIGHT);
		camera = new AsciiCamera(mapDimensions, gameViewArea);

		this.add(terminal);
		this.setSize(SCREEN_WIDTH * 9, SCREEN_HEIGHT * 16);
		this.setLocationRelativeTo(null);
		this.repaint();
	}

	/**
	 *
	 * @return
	 */
	public AsciiPanel getTerminal() {
		return terminal;
	}

	/**
	 * Display the board composed of tiles
	 */
	public void render() {
		camera.lookAt(terminal, world, world.getPlayer().getX(), world.getPlayer().getY());
		terminal.repaint();
	}

	/**
	 * Print GAME OVER message
	 */
	public void renderGameOver() {
		System.out.println("GAME OVER - interface");
	}

	/**
	 * Main thread to handle game FPS and ticks
	 * We are processing keyboard inputs and displaying the board
	 */
	@Override
	public void start() {
		final double FRAMES_PER_SECOND = 60;
		final long TIME_PER_LOOP = (long) (1000000000 / FRAMES_PER_SECOND);
		long startTime;
		long endTime;
		long sleepTime;

		isRunning = true;
		closing = false;
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
		if(closing)
		{
			world = null;
			terminal.setEnabled(false);
			terminal.clear();
			terminal = null;
			camera = null;
			return;
		}
		try {
			// Waiting after showing the GAME OVER screen and before exiting
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	/**
	 * Make the world update by a move
	 */
	public void update() {
		try {
			world.update();
		} catch (Error e) {
			// Game is over
			isRunning = false;
		}
	}

	/**
	 * Process keyboard input
	 */
	@Override
	protected void processInput() {
		world.processInput(kl.getLastInput());
	};
	
	
	@Override
	public void stop()
	{
		closing = true;
		isRunning = false;
	}
	
	@Override
	public void setWorld(World w)
	{
		this.world = w;
	}
}