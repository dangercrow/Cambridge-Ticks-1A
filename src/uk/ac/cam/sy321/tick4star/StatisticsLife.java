package uk.ac.cam.sy321.tick4star;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class StatisticsLife {
	public static void main(String[] args) {
		List<Pattern> patternList = new LinkedList<Pattern>();
		try{
			if (args[0].startsWith("http://"))
			{patternList = PatternLoader.loadFromURL(args[0]);}
			else 
			{patternList = PatternLoader.loadFromDisk(args[0]);}

			LinkedList<Statistics> statList = new LinkedList<Statistics>();
			for (Pattern p:patternList)
			{
				System.out.println("Analysing " + p.getName());
				Statistics stats=analyse(p);
				statList.add(stats);
			}
			System.out.println();
			int[] longStart = {-1,-1};
			int[] longCycle = {-1,-1};
			double fastGrow = -1.0;
			int fastGrowLoc = -1;
			double fastDeath = 0.0;
			int fastDeathLoc = -1;
			int[] maxPop = {-1,-1};
			for (int i = 0; i<statList.size(); i++)
			{
				Statistics s = statList.get(i);
				s.print();
				if (s.getLoopStart()>longStart[1])
				{
					longStart[0]=i;
					longStart[1]=s.getLoopStart();
				}
				if (s.getLoopEnd() - s.getLoopStart() + 1>longCycle[1])
				{
					longCycle[0]=i;
					longCycle[1]=s.getLoopEnd() - s.getLoopStart() + 1;
				}
				if (s.getMaximumGrowthRate()>fastGrow)
				{
					fastGrowLoc=i;
					fastGrow=s.getMaximumGrowthRate();
				}
				if (s.getMaximumDeathRate()>fastDeath)
				{
					fastDeathLoc=i;
					fastDeath=s.getMaximumDeathRate();
				}
				if (s.getMaximumPopulation()>maxPop[1])
				{
					maxPop[0]=i;
					maxPop[1]=s.getMaximumPopulation();
				}

			}
			System.out.println("Longest start: " + patternList.get(longStart[0]).getName() + "(" + longStart[1] + ")");
			System.out.println("Longest cycle: " + patternList.get(longCycle[0]).getName() + "(" + longCycle[1] + ")");
			System.out.println("Biggest growth rate: " + patternList.get(fastGrowLoc).getName() + "(" + fastGrow + ")");
			System.out.println("Biggest death rate: " + patternList.get(fastDeathLoc).getName() + "(" + fastDeath + ")");
			System.out.println("Largest population: " + patternList.get(maxPop[0]).getName() + "(" + maxPop[1] + ")");
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
	public static Statistics analyse(Pattern p)
	{
		boolean[][] world = new boolean[p.getHeight()][p.getWidth()];
		p.initialise(world);
		Statistics stats = new Statistics(world);
		play(world,stats);
		return stats;
	}
	public static void play(boolean [][] world, Statistics stats){
		boolean done = false;
		while (!done)
		{
			world = nextGeneration(world);
			done = stats.update(world);
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
