package uk.ac.cam.sy321.tick6;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import uk.ac.cam.acr31.life.World;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 3003229262147269577L;

	private int zoom = 10; //Number of pixels used to represent a cell
	private int width = 1; //Width of game board in pixels
	private int height = 1;//Height of game board in pixels
	private World current = null;
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	protected void paintComponent(Graphics g) {
		if (current == null) return;
		g.setColor(java.awt.Color.WHITE);
		g.fillRect(0, 0, width, height);
		current.draw(g, width, height);
		if (zoom > 4) {
			g.setColor(java.awt.Color.LIGHT_GRAY);
			for (int i=0; i<width; i = i+zoom){
				g.drawLine(i, 0, i, height);
			}
			for (int i=0; i<height; i = i+zoom){
				g.drawLine(0, i, width, i);
			}
		}
	}
	private void computeSize() {
		if (current == null) return;
		int newWidth = current.getWidth() * zoom;
		int newHeight = current.getHeight() * zoom;
		if (newWidth != width || newHeight != height) {
			width = newWidth;
			height = newHeight;
			revalidate(); //trigger the GamePanel to re-layout its components
		}
	}
	public void display(World w) {
		current = w;
		computeSize();
		repaint();
	}
}
