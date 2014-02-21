package uk.ac.cam.sy321.tick4;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class LoaderLife {
	public static void main(String[] args) {
		List<Pattern> patternList = new LinkedList<Pattern>();
		try{
			if (args[0].startsWith("http://"))
			{patternList = PatternLoader.loadFromURL(args[0]);}
			else 
			{patternList = PatternLoader.loadFromDisk(args[0]);}
			try{
				int index = Integer.parseInt(args[1]);
				if (index < 0 || patternList.size()<=index){
					throw new NumberFormatException("Pattern number isn't in array");
				}
				Pattern p = patternList.get(index);
				boolean[][] world = new boolean[p.getHeight()][p.getWidth()];
				p.initialise(world);
				play(world);
			}
			catch (ArrayIndexOutOfBoundsException e){
				for (int i = 0; i<patternList.size();i++){
					Pattern p=patternList.get(i);
					System.out.println(i+") "+p.format());
				}
			}
		}
		catch (NumberFormatException e){
			System.out.println("Error"+e.getMessage());
		}
		catch (IOException e){
			System.out.println("Error in stream input");
		}
		catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Incorrect number of arguments, expected\n[ URL | FILE ] [INDEX]");
		}
	}
	public static void play(boolean [][] world) throws IOException {
		int userResponse = 10;
		while (userResponse != 'q') {
			if (userResponse == 10){
				print(world);
				world = nextGeneration(world);
			}
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
