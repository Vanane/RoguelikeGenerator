package com.pitchounous.roguelike.entities;

import java.awt.Color;

public class Entity {

	protected int x;
	protected int y;

	protected String type;
	protected Color color;
	protected Color backgroundColor;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public char getGlyph() {
		return type.toUpperCase().charAt(0);
	}

	public String getType() {
		return type;
	}

	public Color getColor() {
		return this.color;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Entity(String type, Color charColor, Color backColor, int xPos, int yPos) {
		x = xPos;
		y = yPos;
		this.type = type;
		color = charColor;
		backgroundColor = backColor;
	}

}
