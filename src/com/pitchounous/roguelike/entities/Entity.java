package com.pitchounous.roguelike.entities;

import java.awt.Color;

import com.pitchounous.roguelike.Roguelike;

public class Entity {

	protected int x;
	protected int y;

	protected String type;
	protected Color color;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public char getGlyph() {
		return type.charAt(0);
	}

	public String getType() {
		return type;
	}

	public Color getColor() {
		return this.color;
	}

	public Entity(String type, String colorString, Integer xPos, Integer yPos) {
		x = xPos;
		y = yPos;
		this.type = type;
		color = Roguelike.stringToColor(colorString);
	}

}
