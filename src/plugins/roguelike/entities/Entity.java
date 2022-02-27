package plugins.roguelike.entities;

import java.awt.Color;

public class Entity {

	protected int x;
	protected int y;

	protected String type;
	protected Color color;
	protected Color backgroundColor;

	/**
	 * 
	 * @param type
	 * @param charColor
	 * @param backColor
	 * @param xPos
	 * @param yPos
	 */
	public Entity(String type, Color charColor, Color backColor, int xPos, int yPos) {
		x = xPos;
		y = yPos;
		this.type = type;
		color = charColor;
		backgroundColor = backColor;
	}

	/**
	 * 
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @return
	 */
	public char getGlyph() {
		return type.toUpperCase().charAt(0);
	}

	/**
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @return
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * 
	 * @return
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}
}
