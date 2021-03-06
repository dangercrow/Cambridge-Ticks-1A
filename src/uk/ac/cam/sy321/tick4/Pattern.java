package uk.ac.cam.sy321.tick4;

public class Pattern {
	private String name;
	private String author;
	private int width;
	private int height;
	private int startCol;
	private int startRow;
	private String cells;

	//Bunch of getters
	public String getName() {return name;}
	public String getAuthor() {return author;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public int getStartCol() {return startCol;}
	public int getStartRow() {return startRow;}
	public String getCells() {return cells;}
	//End getters

	public Pattern(String format) throws PatternFormatException {
		//NAME:AUTHOR:WIDTH:HEIGHT:STARTCOL:STARTROW:CELLS
		String[] formatStrings = format.split(":");
		if (formatStrings.length!=7) {
			throw new PatternFormatException("Incorrect number of arguments in pattern");
		}
		name = formatStrings[0];
		author = formatStrings[1];
		cells = formatStrings[6];
		for (char i:cells.toCharArray()) {
			if (i!='0' & i!='1' & i!=' ') throw new PatternFormatException("Cells contain illegal values");
		}
		try{
			width = Integer.parseInt(formatStrings[2]);
			height = Integer.parseInt(formatStrings[3]);
			startCol = Integer.parseInt(formatStrings[4]);
			startRow = Integer.parseInt(formatStrings[5]);
		}
		catch (NumberFormatException e){
			throw new PatternFormatException("Unable to convert string to value "+e.getMessage());
		}

	}
	public void initialise(boolean[][] world) {
		String[] cellStrings = this.getCells().split(" "); 
		for (int cellrow = 0; cellrow < cellStrings.length; cellrow++) {
			char[] cells = cellStrings[cellrow].toCharArray();
			for (int cellcol = 0; cellcol < cells.length; cellcol++){
				world[cellrow+startRow][cellcol+startCol] = Character.getNumericValue(cells[cellcol])==1;
			}
		}
	}
	public String format() {
		return getName()+":"+getAuthor()+":"+getWidth()+":"+getHeight()+":"+getStartRow()+":"+getCells();
	}
}
