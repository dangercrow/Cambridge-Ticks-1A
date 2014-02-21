package uk.ac.cam.sy321.tick5;

public class PackedWorld extends WorldImpl {
	private long state;
	protected PackedWorld() {
		super(8, 8);
		state = 0L;
	}

	protected PackedWorld(PackedWorld prev) {
		super(prev);
		state = 0L;
	}

	@Override
	public boolean getCell(int col, int row) {
		int h = getHeight();
		int w = getWidth();
		return (0<=row && row<h && 0<=col && col<w)?PackedLong.get(state, row*8+col):false;
	}

	@Override
	public void setCell(int col, int row, boolean alive) {
		int h = getHeight();
		int w = getWidth();
		if (0<=row && row<h && 0<=col && col<w){
			state=PackedLong.set(state, row*8+col, alive);
		}
	}

	@Override
	protected WorldImpl nextGeneration() {
		PackedWorld temp = new PackedWorld(this);
		for (int row = 0; row < 8; row++){
			for (int col = 0; col < 8; col++){
				temp.setCell(col, row, computeCell(col, row));
			}
		}
		return temp;
	}

}
