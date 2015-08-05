package minesweeper.core;

import minesweeper.core.Tile.State;

/**
 * Field represents playing field and game logic.
 */
public class Field {
	/**
	 * Playing field tiles.
	 */
	private final Tile[][] tiles;

	/**
	 * Field row count. Rows are indexed from 0 to (rowCount - 1).
	 */
	private final int rowCount;

	/**
	 * Column count. Columns are indexed from 0 to (columnCount - 1).
	 */
	private final int columnCount;

	/**
	 * Mine count.
	 */
	private final int mineCount;

	/**
	 * Game state.
	 */
	private GameState state = GameState.PLAYING;

	/**
	 * Constructor.
	 * 
	 * @param rowCount
	 *            - row count
	 * @param columnCount
	 *            - column count
	 * @param mineCount
	 *            - mine count
	 */
	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];

		// generate the field content
		generate();
	}

	public void openMine() {
		for (int row = 0; row < getRowCount(); row++) {
			for (int column = 0; column < getColumnCount(); column++) {
				Tile tile = tiles[row][column];
				if ((tile.getState() == State.CLOSED) && (tile instanceof Mine)) {
					openTile(row, column);
				}
			}
		}
	}

	/**
	 * Opens tile at specified indeces.
	 * 
	 * @param row
	 *            - row number
	 * @param column
	 *            - column number
	 */

	public void openTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.OPEN);
			if (tile instanceof Mine) {
				state = GameState.FAILED;
				return;
			} else {
				Clue clue = (Clue) tile;
				if (clue.getValue() == 0) {
					openAdjacentTiles(row, column);
				}
			}

			if (isSolved()) {
				state = GameState.SOLVED;
				return;
			}
		}
	}

	/**
	 * Marks tile at specified indexes.
	 * 
	 * @param row
	 *            - row number
	 * @param column
	 *            - column number
	 */
	public void markTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.MARKED);
		} else {
			if (tile.getState() == Tile.State.MARKED) {
				tile.setState(Tile.State.CLOSED);
			}
		}
	}

	/**
	 * Generates playing field. Generate Mines by random generating position of
	 * Mine Fill empty Tile of field with Clue Tile
	 */
	private void generate() {
		int positionX;
		int positionY;
		int count = 0;
		do {
			positionX = (int) (Math.random() * rowCount);
			positionY = (int) (Math.random() * columnCount);
			if (tiles[positionX][positionY] == null) {
				tiles[positionX][positionY] = new Mine();
				count++;
			}
		} while (count != mineCount);
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] == null) {
					tiles[row][column] = new Clue(countAdjacentMines(row, column));
				}
			}
		}
	}

	/**
	 * Returns true if game is solved, false otherwise.
	 * 
	 * @return true if game is solved, false otherwise
	 */
	private boolean isSolved() {
		return getColumnCount() * getRowCount() - getNumberOf(State.OPEN) == getMineCount();
	}

	/**
	 * count Tiles with chosen state
	 * 
	 * @param state
	 *            - chosen state
	 * @return number - count Tiles
	 */
	public int getNumberOf(Tile.State state) {
		int number = 0;
		for (int row = 0; row < getRowCount(); row++) {
			for (int column = 0; column < getColumnCount(); column++) {
				if (tiles[row][column].getState() == state) {
					number++;
				}
			}
		}
		return number;
	}

	public void openAdjacentTiles(int row, int column) {
		if (countAdjacentMines(row, column) == 0) {
			for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
				int actRow = row + rowOffset;
				if (actRow >= 0 && actRow < getRowCount()) {
					for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
						int actColumn = column + columnOffset;
						if (actColumn >= 0 && actColumn < getColumnCount()) {
							openTile(actRow, actColumn);
						}
					}
				}
			}
		}

	}

	/**
	 * Returns number of adjacent mines for a tile at specified position in the
	 * field.
	 * 
	 * @param row
	 *            - row number.
	 * @param column
	 *            - column number.
	 * @return number of adjacent mines.
	 */
	public int countAdjacentMines(int row, int column) {
		int count = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < getRowCount()) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < getColumnCount()) {
						if (tiles[actRow][actColumn] instanceof Mine) {
							count++;
						}
					}
				}
			}
		}

		return count;
	}

	/**
	 * return value of Mine
	 * 
	 * @return mineCount - number of Mine in field
	 */
	public int getMineCount() {
		return mineCount;
	}

	/**
	 * return value of Column
	 * 
	 * @return columnCount - number of column in field
	 */
	public int getColumnCount() {
		return columnCount;
	}

	/**
	 * return value of Row
	 * 
	 * @return rowCount - number of row in field
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * return state of game
	 * 
	 * @return state - state of game
	 */
	public GameState getState() {
		return state;
	}

	/**
	 * return Tile at specific position
	 * 
	 * @param row
	 *            - row number.
	 * @param column
	 *            - column number.
	 * @return tiles - representant of Tile
	 */
	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "";
	}
}
