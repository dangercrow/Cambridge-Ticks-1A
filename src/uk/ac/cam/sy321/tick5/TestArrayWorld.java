package uk.ac.cam.sy321.tick5;

import uk.ac.cam.acr31.life.World;
import java.io.Writer;
import java.awt.Graphics;
import java.io.PrintWriter;

public class TestArrayWorld implements World {
	private int generation;
	private int width;
	private int height;
	private boolean[][] cells;

	public TestArrayWorld(int w, int h) {
		width = w;
		height = h;
		generation = 0;
		cells = new boolean[h][w]; 
	}

	protected TestArrayWorld(TestArrayWorld prev) {
		width = prev.width;
		height = prev.height;
		generation = prev.generation + 1;
		cells = new boolean[height][width];
	}

	public void setCell(int col, int row, boolean alive) {
		if (0<=row && row<height && 0<=col && col<width){
			cells[row][col] = alive;
		}
	}

	public boolean getCell(int col, int row) { 
		return (0<=col&&col<width && 0<=row&&row<height) ? cells[row][col] : false; 
	}
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getGeneration() { return generation; }
	public void draw(Graphics g, int width, int height) { /*Leave empty*/ }
	public int getPopulation() { return 0;	}

	public void print(Writer w) {
		PrintWriter pw = new PrintWriter(w);
		pw.println("-");
		for (int row = 0; row < height; row++) { 
			for (int col = 0; col < width; col++) {
				pw.print(getCell(col, row) ? "#" : "_");
			}
			pw.println();
			pw.flush();
		}
	}

	private TestArrayWorld nextGeneration() {
		//Construct a new TestArrayWorld object to hold the next generation:
		TestArrayWorld world = new TestArrayWorld(this);
		for (int row = 0; row<height; row++){
			for (int col = 0; col<width; col++){
				world.setCell(col, row, computeCell(col, row));
			}
		}
		return world;
	}

	public TestArrayWorld nextGeneration(int log2StepSize) { 
		TestArrayWorld world = this;
		for (int i = 0; i<(1<<log2StepSize); i++)
		{
			world = world.nextGeneration();
		}
		return world;
	}

	private int countNeighbours(int col,int row){
		int total = 0;
		total = getCell(col-1, row-1) ? total + 1 : total;
		total = getCell( col , row-1) ? total + 1 : total;
		total = getCell(col+1, row-1) ? total + 1 : total;
		total = getCell(col-1,  row ) ? total + 1 : total;
		total = getCell(col+1,  row ) ? total + 1 : total;
		total = getCell(col-1, row+1) ? total + 1 : total;
		total = getCell( col , row+1) ? total + 1 : total;
		total = getCell(col+1, row+1) ? total + 1 : total;
		return total;
	}
	private boolean computeCell(int col,int row){
		int neighbours = countNeighbours(col,row);
		return (neighbours==3 || neighbours==2 && getCell(col,row));
	}

}