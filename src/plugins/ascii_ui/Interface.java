package plugins.ascii_ui;

import java.awt.Rectangle;

import javax.swing.JFrame;

import com.pitchounous.roguelike.ui.BasicUI;
import com.pitchounous.roguelike.ui.KeyHandler;
import com.pitchounous.roguelike.world.World;

import asciiPanel.AsciiPanel;

public class Interface extends JFrame implements BasicUI {

	private static final long serialVersionUID = 6408617006915516474L;

	private AsciiPanel terminal;
	private AsciiCamera camera;

	private KeyHandler kl;
	private boolean isRunning;

	private World world;

	private final int SCREEN_WIDTH = 80;
	private final int SCREEN_HEIGHT = 60;

	public Interface(World world) {
		super("Roguelike | Pitchounous");
		this.world = world;

		Rectangle mapDimensions = new Rectangle(world.width, world.height);
		Rectangle gameViewArea = new Rectangle(SCREEN_WIDTH, SCREEN_HEIGHT - 5);
		terminal = new AsciiPanel(SCREEN_WIDTH, SCREEN_HEIGHT);
		camera = new AsciiCamera(mapDimensions, gameViewArea);

		kl = new KeyHandler();
		isRunning = false;

		add(terminal);
		addKeyListener(kl);
		setSize(SCREEN_WIDTH * 9, SCREEN_HEIGHT * 16);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		repaint();
	}

	public AsciiPanel getTerminal() {
		return terminal;
	}

	public void render() {
		System.out.println("render called");
		camera.lookAt(terminal, world, world.getPlayer().getX(), world.getPlayer().getY());
		terminal.repaint();
	}

	public void renderGameOver(){
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
	public void processInput() {
        world.processInput(kl.getLastInput());
	}
}