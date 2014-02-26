package uk.ac.cam.sy321.tick5star;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import uk.ac.cam.acr31.sound.AudioSequence;
import uk.ac.cam.acr31.sound.SineWaveSound;

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
		boolean trimSparse = true; //Controls whether long sequences of empty sound frames should be trimmed
		AudioSequence output = new AudioSequence(60.0/BPM);
		output.advance();
		try{
			FileOutputStream fd = new FileOutputStream(new File(args[2]));
			Pattern p = new Pattern(args[0]);
			int gens = Integer.parseInt(args[1]);
			boolean[][] world = new boolean[p.getHeight()][p.getWidth()];
			p.initialise(world);
			//Had to hard-code frequencies in... one octave per line
			final double[] freqs = {4186.01, 3951.07, 3729.31, 3520, 3322.44, 3135.96, 2959.96, 2793.83, 2637.02, 2489.02, 2349.32, 2217.46,
					2093, 1975.53, 1864.66, 1760, 1661.22, 1567.98, 1479.98, 1396.91, 1318.51, 1244.51, 1174.66, 1108.73,
					1046.5, 987.767, 932.328, 880, 830.609, 783.991, 739.989, 698.456, 659.255, 622.254, 587.33, 554.365, 523.251,
					493.883, 466.164, 440, 415.305, 391.995, 369.994, 349.228, 329.628, 311.127, 293.665, 277.183, 261.626, 
					246.942, 233.082, 220, 207.652, 195.998, 184.997, 174.614, 164.814, 155.563, 146.832, 138.591, 130.813,
					123.471, 116.541, 110, 103.826, 97.9989, 92.4986, 87.3071, 82.4069};
			int lowNote = 36 - p.getWidth()/2; //One of the central cells makes C5
			for (int i = 0; i<gens; i++)
			{
				boolean[] sparseness = new boolean[world.length];
				for (int j = 0; j<world.length;j++)
				{
					boolean isEmpty=true;
					for (int k = 0; k<world.length;k++)
					{
						if (world[j][k]){
							int note = lowNote+k < 0 ? 0 : (lowNote+k > 67 ? 67 : lowNote+k); //pull low/high notes in
							output.addSound(new SineWaveSound(freqs[note],1.0/world[j].length));
							isEmpty=false;
						}
					}
					sparseness[j] = isEmpty;
					if (trimSparse){
						try
						{
							if (!sparseness[j] || !sparseness[j-1] || !sparseness[j-2]){
								output.advance(); //Allows a maximum of 2 blank sound-frames between generations
							}
							//Else: Row trimmed at Generation i - Row j
						}
						catch (ArrayIndexOutOfBoundsException e)
						{//First few frames, allow it to be empty noise
							output.advance();
						}
					}
					else
					{
						output.advance();
					}
				}
				world = nextGeneration(world);
			}
			output.write(fd);
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
