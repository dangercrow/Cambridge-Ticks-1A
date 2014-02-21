package uk.ac.cam.sy321.tick4star;

import java.util.Arrays;
import java.util.LinkedList;

public class Statistics {
	private int loopStart;
	private int loopEnd;
	private int maxPop;
	private double maxGrowth;
	private double maxDeath;
	private LinkedList<boolean[][]> worlds;
	private int prevPop;
	private int currentPop;

	//Buncha getters
	public double getMaximumGrowthRate() {return maxGrowth;}
	public double getMaximumDeathRate() {return maxDeath;}
	public int getLoopStart() {return loopStart;}
	public int getLoopEnd() {return loopEnd;}
	public int getMaximumPopulation() {return maxPop;}

	public boolean update(boolean[][] world)
	{
		prevPop = currentPop;
		currentPop=0;
		for (boolean[] i:world)
		{
			for (boolean j:i)
			{
				if (j) currentPop++;
			}
		}
		maxPop = max(maxPop,currentPop);
		if (prevPop>0){
			maxGrowth = max(maxGrowth, (double) (currentPop-prevPop)/prevPop);
			maxDeath =  max(maxDeath,  (double) (prevPop-currentPop)/prevPop);
		}
		int loc = location(worlds,world);
		if (loc!=-1)
		{
			loopStart=loc;
			loopEnd=worlds.size()-1;
			return true;
		}
		worlds.add(world);
		return false;
	}

	public double max(double one, double two) //Ease of reading for above
	{
		return one>two?one:two;
	}
	public int max(int one, int two) //Ease of reading for above, for ints
	{
		return one>two?one:two;
	}
	public int location(LinkedList<boolean[][]> list, boolean[][] item) //Find first occurrence of item in list, -1 on not found
	{
		for (int i=0; i<list.size(); i++)
		{
			if (Arrays.deepEquals(item, list.get(i)))
			{
				return i;
			}
		}
		return -1;
	}
	public Statistics(boolean[][] world)
	{
		//Reset all variables;
		loopStart=-1;
		loopEnd=-1;
		maxPop=0;
		maxGrowth=0.0;
		maxDeath=0.0;
		worlds = new LinkedList<boolean[][]>();
		prevPop=0;
		currentPop=0;

		for (boolean[] i:world)
		{
			for (boolean j:i)
			{
				currentPop = j ? currentPop+1 : currentPop;
			}
		}
		maxPop = currentPop;
		worlds.add(world);
	}
	public void print()
	{
		System.out.print(loopStart);
		System.out.print(" / ");
		System.out.print(loopEnd);
		System.out.print(" / ");
		System.out.print(maxPop);
		System.out.print(" / ");
		System.out.print(maxGrowth); //TODO
		System.out.print(" / ");
		System.out.println(maxDeath); //TODO
	}
}
