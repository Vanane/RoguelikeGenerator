package plugins.terminal_ui;

import java.awt.Point;
import java.awt.Rectangle;

import com.pitchounous.roguelike.entities.creatures.Creature;
import com.pitchounous.roguelike.world.World;
import com.pitchounous.roguelike.world.tiles.Tile;

import asciiPanel.AsciiPanel;

public class AsciiCamera {

    int screenWidth;
    int screenHeight;
    int mapWidth;
    int mapHeight;

    public AsciiCamera(Rectangle Bounds, Rectangle viewArea) {
        screenWidth = viewArea.width;
        screenHeight = viewArea.height;

        mapWidth = Bounds.width;
        mapHeight = Bounds.height;
    }

    public Point getCameraOrigin(int xfocus, int yfocus) {
        int spx = Math.max(0, Math.min(xfocus - screenWidth / 2, mapWidth - screenWidth));
        int spy = Math.max(0, Math.min(yfocus - screenHeight / 2, mapHeight - screenHeight));
        return new Point(spx, spy);
    }

    public void lookAt(AsciiPanel terminal, World world, int xfocus, int yfocus) {
        Point origin = getCameraOrigin(xfocus, yfocus);

        Tile tile;
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                tile = world.getTile(origin.x + x, origin.y + y);
                if (tile != null)
                    terminal.write(tile.getGlyph(), x, y, tile.getColor(), tile.getBackgroundColor());
            }
        }

        int spx;
        int spy;
        for (Creature entity : world.getAliveCreatures()) {
            spx = entity.getX() - origin.x;
            spy = entity.getY() - origin.y;

            if ((spx >= 0 && spx < screenWidth) && (spy >= 0 && spy < screenHeight)) {
                terminal.write(
                        entity.getGlyph(), spx, spy,
                        entity.getColor(),
                        entity.getBackgroundColor());
            }
        }
    }
}