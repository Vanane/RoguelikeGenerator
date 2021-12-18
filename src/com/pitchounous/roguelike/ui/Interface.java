package com.pitchounous.roguelike.ui;

import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Queue;

import javax.swing.JFrame;

import com.pitchounous.roguelike.world.World;
import com.pitchounous.roguelike.world.tiles.Tile;

import asciiPanel.AsciiPanel;

public class Interface extends JFrame implements KeyListener, MouseListener {

	private static final long serialVersionUID = 6408617006915516474L;

	private AsciiPanel terminal;
	private AsciiCamera camera;
	private Queue<InputEvent> inputQueue;

	private int screenWidth;
	private int screenHeight;

	public Interface(int screenWidth, int screenHeight, Rectangle mapDimensions) {
		super("Roguelike | Pitchounous");

		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		inputQueue = new LinkedList<>();

		Rectangle gameViewArea = new Rectangle(screenWidth, screenHeight - 5);
		terminal = new AsciiPanel(screenWidth, screenHeight);
		camera = new AsciiCamera(mapDimensions, gameViewArea);

		super.add(terminal);
		super.addKeyListener(this);
		super.addMouseListener(this);
		super.setSize(screenWidth * 9, screenHeight * 16);
		super.setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.repaint();
	}

	public AsciiPanel getTerminal() {
		return terminal;
	}

	public InputEvent getNextInput() {
		return inputQueue.poll();
	}

	public void pointCameraAt(World world, int xfocus, int yfocus) {
		camera.lookAt(terminal, world, xfocus, yfocus);
	}

	public void refresh() {
		terminal.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		inputQueue.add(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
