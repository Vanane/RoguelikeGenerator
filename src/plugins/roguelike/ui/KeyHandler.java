package plugins.roguelike.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeyHandler implements KeyListener {

    List<KeyEvent> keys = new ArrayList<>();

    /**
     * Generic keyboard input handler
     *
     * @return
     */
    public KeyEvent getLastInput() {
        if (keys.size() == 0)
            return null;

        KeyEvent ke = this.keys.get(0);
        this.keys.remove(ke);
        return ke;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys.add(e);
    }
}