package uk.ac.cam.sy321.tick6;

public class ArrayWorld extends WorldImpl { 
	private boolean[][] cells;

	public ArrayWorld(int width, int height){
		super(width,height);
		cells = new boolean[height][width];
	}

	public ArrayWorld(ArrayWorld prev){
		super(prev);
		cells = new boolean[prev.getHeight()][prev.getWidth()];
	}

	@Override
	public boolean getCell(int col, int row) {
		int h = getHeight();
		int w = getWidth();
		return (0<=row && row<h && 0<=col && col<w)?cells[row][col]:false;
	}

	@Override
	public void setCell(int col, int row, boolean alive) {
		int h = getHeight();
		int w = getWidth();
		if (0<=row && row<h && 0<=col && col<w){
			cells[row][col]=alive;
		}
	}

	@Override
	protected WorldImpl nextGeneration() {
		ArrayWorld temp = new ArrayWorld(this);
		for (int row = 0; row < getHeight(); row++){
			for (int col = 0; col < getWidth(); col++){
				temp.setCell(col, row, computeCell(col, row));
			}
		}
		return temp;
	}

}
