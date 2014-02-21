package uk.ac.cam.sy321.tick3star;

import uk.ac.cam.sy321.tick3star.Pattern;

public class AnimatedLife {
	public static void main(String[] args) throws Exception {
		if (args.length!=3) return;
		Pattern p = new Pattern(args[0]);
		int iters = new Integer(args[1]);
		boolean[][] world = new boolean[p.getHeight()][p.getWidth()];
		p.initialise(world);
		play(world,iters,args[2]);
	}
	public static void play(boolean [][] world, int iters, String fileloc) throws Exception {
		OutputAnimatedGif out = new OutputAnimatedGif(fileloc);
		for (int i=0; i<iters; i++)
		{
			out.addFrame(world);
			world = nextGeneration(world);
		}
		out.close();
	}
	public static void print(boolean [][] world) { 
		System.out.println("-"); 
		for (int row = 0; row < world.length; row++) { 
			for (int col = 0; col < world[row].length; col++) {
				System.out.print(getCell(world, col, row) ? "#" : "_"); 
			}
			System.out.println(); 
		} 
	}
	public static int countNeighbours(boolean [][] world, int col, int row){
		int total = 0;
		total = getCell(world, col-1, row-1) ? total + 1 : total;
		total = getCell(world,  col , row-1) ? total + 1 : total;
		total = getCell(world, col+1, row-1) ? total + 1 : total;
		total = getCell(world, col-1,  row ) ? total + 1 : total;
		total = getCell(world, col+1,  row ) ? total + 1 : total;
		total = getCell(world, col-1, row+1) ? total + 1 : total;
		total = getCell(world,  col , row+1) ? total + 1 : total;
		total = getCell(world, col+1, row+1) ? total + 1 : total;
		return total;
	}
	public static boolean computeCell(boolean [][] world,int col,int row) {
		boolean liveCell = getCell(world, col, row);
		int neighbours = countNeighbours(world, col, row);
		//Cells are always dead UNLESS 3 neighbours or 2 neighbours and already alive.
		return (neighbours == 3 || neighbours == 2 && liveCell);
	}
	public static boolean [][] nextGeneration(boolean [][] world){
		boolean[][] nextWorld = new boolean[world.length][];
		for (int row = 0; row < world.length; row++){
			//Make the row dynamically
			nextWorld[row] = new boolean[world[row].length];
			//Initialised empty, filled by next loop
			for (int col = 0; col < world[row].length; col++){
				setCell(nextWorld, col, row, computeCell(world,col,row));
			}
		}
		return nextWorld;
	}
	public static boolean getCell(boolean[][] world, int col, int row) {
		if (row < 0 || row > world.length - 1) return false;
		if (col < 0 || col > world[row].length - 1) return false;
		return world[row][col];
	}
	public static void setCell(boolean [][] world, int col, int row, boolean value){
		if (0<=row && row<world.length){
			if (0<=col && col<world[row].length){
				world[row][col] = value;	
			}
		}
	}
}
