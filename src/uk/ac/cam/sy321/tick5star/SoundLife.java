package uk.ac.cam.sy321.tick5star;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import uk.ac.cam.acr31.sound.AudioSequence;
import uk.ac.cam.acr31.sound.SoundOverflowException;

public class SoundLife {
	public static void main(String[] args)
	{
		if (args.length!=3)
		{
			System.out.println("Incorrect number of arguments, expected:\n"
					+ "[PATTERN] [NUMBER OF GENERATIONS] [OUTPUT STREAM NAME]");
			return;
		}
		double BPM = 240.0;
		AudioSequence output = new AudioSequence(60.0/BPM);
		try{
			Pattern p = new Pattern(args[0]);
			int gens = Integer.parseInt(args[2]);
			boolean[][] world = new boolean[p.getHeight()][p.getWidth()];
			p.initialise(world);
			//TODO Calculate middle note
			for (int i = 0; i<gens; i++)
			{
				for (int j = 0; j<world.length;j++)
				{
					for (int k = 0; k<world.length;k++)
					{
						//TODO: Make - Write Sound
					}
				}
				output.advance();
				world = nextGeneration(world);
			}
			output.write(new FileOutputStream(new File(args[2])));
		}
		catch (PatternFormatException e)
		{
			System.out.println("PatternFormatException: " + e.getMessage());
			return;
		}
		catch (NumberFormatException e)
		{
			System.out.println("Badly formated number: " + e.getMessage());
			return;
		}
		catch (IOException e)
		{
			System.out.println("File IO Error: " + e.getMessage());
			return;
		}
		catch (SoundOverflowException e)
		{
			System.out.println("Sound Overflow Error: " + e.getMessage());
			return;
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
