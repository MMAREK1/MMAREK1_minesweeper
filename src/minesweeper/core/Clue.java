package minesweeper.core;

/**
 * Clue tile.
 */
public class Clue extends Tile {
	/** Value of the clue. */
	private final int value;

	/**
	 * Constructor.
	 * 
	 * @param value
	 *            value of the clue
	 */
	public Clue(int value) {
		this.value = value;
	}

	/**
	 * return value of Clue
	 * 
	 * @return value - number of Mine
	 */
	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if (this.getState() == State.OPEN)
			return value + "";
		else
			return super.toString();
	}
}
