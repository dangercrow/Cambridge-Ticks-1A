package uk.ac.cam.sy321.tick2;

public class TinyLife {
	public static void main(String[] args) throws Exception{
		play(Long.decode(args[0]));
	}
	public static void play(long world) throws Exception {
		int userResponse = 0;
		while (userResponse != 'q') {
			print(world);
			userResponse = System.in.read();
			world = nextGeneration(world);
		}
	}
	public static void print(long world) { 
		System.out.println("-"); 
		for (int row = 0; row < 8; row++) { 
			for (int col = 0; col < 8; col++) {
				System.out.print(getCell(world, col, row) ? "#" : "_"); 
			}
			System.out.println(); 
		} 
	}
	public static int countNeighbours(long world, int col, int row){
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
	public static boolean computeCell(long world,int col,int row) {
		// liveCell is true if the cell at position (col,row) in world is live
		boolean liveCell = getCell(world, col, row);

		// neighbours is the number of live neighbours to cell (col,row)
		int neighbours = countNeighbours(world, col, row);
		// we will return this value at the end of the method to indicate whether 
		// cell (col,row) should be live in the next generation
		boolean nextCell = false;
		//Dead unless explicitly alive

		//A live cell with two or three neighbours lives (a balanced population)
		if (neighbours == 2 && liveCell){
			nextCell = true; // Three is handled below, previous state is irrelevant
		}
		//A dead cell with exactly three live neighbours comes alive
		if (neighbours == 3){
			nextCell = true; // three neighbours makes cell alive, regardless of prev state
		}
		return nextCell;
	}
	public static long nextGeneration(long world){
		long nextWorld = 0x0L;
		for (int row = 0; row < 8; row++){
			for (int col = 0; col < 8; col++){
				nextWorld=setCell(nextWorld, col, row, computeCell(world,col,row));
			}
		}
		return nextWorld;
	}

	public static boolean getCell(long world, int col, int row){
		if (0<=col & col<8 & 0<=row & row<8){
			return PackedLong.get(world, col + row*8);
		}
		return false;
	}
	public static long setCell(long world, int col, int row, boolean value){
		if (0<=col & col<=8 & 0<=row & row<=8){
			world = PackedLong.set(world, col+row*8,value);
		}
		return world;
	}

}
