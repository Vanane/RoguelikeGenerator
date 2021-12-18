package com.pitchounous.roguelike.entities;

import java.awt.Color;

import com.pitchounous.roguelike.Roguelike;

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

	public Entity(String type, String charColor, String backColor, int xPos, int yPos) {
		x = xPos;
		y = yPos;
		this.type = type;
		color = Roguelike.stringToColor(charColor);
		backgroundColor = Roguelike.stringToColor(backColor);
	}

}
