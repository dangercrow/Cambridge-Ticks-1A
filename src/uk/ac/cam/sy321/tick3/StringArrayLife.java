package uk.ac.cam.sy321.tick3;

public class StringArrayLife {
	public static void main(String[] args) throws Exception {
		String[] formatStrings = args[0].split(":");
		//NAME:AUTHOR:WIDTH:HEIGHT:STARTCOL:STARTROW:CELLS
		int width = Integer.parseInt(formatStrings[2]);
		int height = Integer.parseInt(formatStrings[3]);
		int startCol = Integer.parseInt(formatStrings[4]);
		int startRow = Integer.parseInt(formatStrings[5]);
		//All fine so far.
		String[] cellStrings = formatStrings[6].split(" ");
		boolean[][] world = new boolean[height][width];
		for (int cellrow = 0; cellrow < cellStrings.length; cellrow++) {
			char[] cells = cellStrings[cellrow].toCharArray();
			for (int cellcol = 0; cellcol < cells.length; cellcol++){
				setCell(world, cellrow+startRow, cellcol+startCol, Character.getNumericValue(cells[cellcol])==1);
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
		return (neighbours == 3 || neighbours == 2 & liveCell);
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
