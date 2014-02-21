package uk.ac.cam.sy321.tick3;

import uk.ac.cam.sy321.tick3.PackedLong;

public class ArrayLife {
	public static void main(String[] args) throws Exception {
		int size = Integer.parseInt(args[0]);
		long initial = Long.decode(args[1]);
		boolean[][] world = new boolean[size][size];
		//place the long representation of the game board in the centre of "world"
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				world[i+size/2-4][j+size/2-4] = PackedLong.get(initial,i*8+j);
			}
		}
		play(world);
	}
	public static void play(boolean [][] world) throws Exception {
		int userResponse = 10; // Initialise to \n (or \r?)
		while (userResponse != 'q') {
			if (userResponse == 10) // ONLY ITERATE ONCE ON ENTER (repr as 13,10)
			{//Kept iterating n times where n is number of bytes in line input
				print(world);
				world = nextGeneration(world);
			}
			//Keep blocking call outside, lest it spiral off
			userResponse = System.in.read();
		}
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
		int maxWidth = 0;
		for (boolean [] i:world){
			maxWidth = i.length > maxWidth ? i.length : maxWidth;
		}
		boolean[][] nextWorld = new boolean[world.length][maxWidth];
		//Made a new array of same dimensions as world
		//Works since always square in this example, can be
		//extended to work on n x m later
		for (int row = 0; row < world.length; row++){
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
