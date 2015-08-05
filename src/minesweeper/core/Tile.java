package minesweeper.core;

/**
 * Tile of a field.
 */
public abstract class Tile {

	/** Tile states. */
	public enum State {
		/** Open tile. */
		OPEN, /** Closed tile. */
		CLOSED, /** Marked tile. */
		MARKED
	}

	/** Tile state. */
	private State state = State.CLOSED;

	/**
	 * Returns current state of this tile.
	 * 
	 * @return current state of this tile
	 */
	public State getState() {
		return state;
	}

	/**
	 * Sets current current state of this tile.
	 * 
	 * @param state
	 *            current state of this tile
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * return Tile at specific position
	 * 
	 * @param row
	 *            - row number
	 * @param column
	 *            - column number
	 * @return tile - Tile
	 */
	public Tile getTile(int row, int column) {
		Tile tile = null;
		return tile;
	}

	@Override
	public String toString() {
		if (this.getState() == State.CLOSED)
			return "-";
		else
			return "M";

	}

}
