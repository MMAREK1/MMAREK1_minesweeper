package test;

import static org.junit.Assert.*;

import org.junit.Test;

import minesweeper.core.Clue;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Mine;
import minesweeper.core.Tile;
import minesweeper.core.Tile.State;

public class FieldTest {
	static final int ROWS = 9;
	static final int COLUMNS = 10;
	static final int MINES = 10;

	@Test
	public void isSolved() {
		Field field = new Field(ROWS, COLUMNS, MINES);

		assertEquals(GameState.PLAYING, field.getState());

		int open = 0;
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				if (field.getTile(row, column) instanceof Clue) {
					field.openTile(row, column);
					open++;
				}
				if (field.getRowCount() * field.getColumnCount() - open == field.getMineCount()) {
					assertEquals(GameState.SOLVED, field.getState());
				} else {
					assertNotSame(GameState.FAILED, field.getState());
				}
			}
		}

		assertEquals(GameState.SOLVED, field.getState());
	}

	@Test
	public void generate() {
		Field field = new Field(ROWS, COLUMNS, MINES);
		assertEquals(ROWS, field.getRowCount());
		assertEquals(COLUMNS, field.getColumnCount());
		assertEquals(MINES, field.getMineCount());
		int mineCount = 0;
		int clueCount = 0;
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				assertNotNull(tile);
				if (tile instanceof Mine) {
					mineCount++;
				} else {
					clueCount++;
				}
			}
		}
		assertEquals(MINES, mineCount);
		assertEquals(ROWS * COLUMNS - MINES, clueCount);
	}

	/*
	 * @Test public void openMine() { Field field = new
	 * Field(ROWS,COLUMNS,MINES); for (int row = 0; row < field.getRowCount();
	 * row++) { for (int column = 0; column < field.getColumnCount(); column++)
	 * { Tile tile = field.getTile(row, column); if(tile instanceof Mine) {
	 * assertEquals(tile.getState(),Tile.State.OPEN); } } } }
	 */

	@Test
	public void openTile() {
		Field field = new Field(ROWS, COLUMNS, MINES);
		Tile tileOpen = field.getTile(1, 2);
		tileOpen.setState(State.OPEN);
		field.openTile(1, 2);
		assertEquals(State.OPEN, tileOpen.getState());
		Tile tileMarked = field.getTile(2, 3);
		tileMarked.setState(State.MARKED);
		field.openTile(2, 3);
		assertEquals(State.MARKED, tileMarked.getState());
		Tile tileClosed = field.getTile(3, 4);
		tileClosed.setState(State.CLOSED);
		field.openTile(3, 4);
		assertEquals(State.OPEN, tileClosed.getState());
	}

	@Test
	public void markTile() {
		Field field = new Field(ROWS, COLUMNS, MINES);
		Tile tileOpen = field.getTile(1, 2);
		tileOpen.setState(State.OPEN);
		field.markTile(1, 2);
		assertEquals(State.OPEN, tileOpen.getState());
		Tile tileMarked = field.getTile(2, 3);
		tileMarked.setState(State.MARKED);
		field.markTile(2, 3);
		assertEquals(State.CLOSED, tileMarked.getState());
		Tile tileClosed = field.getTile(3, 4);
		tileClosed.setState(State.CLOSED);
		field.markTile(3, 4);
		assertEquals(State.MARKED, tileClosed.getState());
	}

	@Test
	public void getNumberOf() {
		Field field = new Field(ROWS, COLUMNS, MINES);
		int openCount = 0;
		int closedCount = 0;
		int markedCount = 0;
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				switch (tile.getState()) {
				case CLOSED:
					closedCount++;
					break;
				case OPEN:
					openCount++;
					break;
				case MARKED:
					markedCount++;
				default:
					break;
				}
			}
		}
		assertEquals(openCount, field.getNumberOf(State.OPEN));
		assertEquals(closedCount, field.getNumberOf(State.CLOSED));
		assertEquals(markedCount, field.getNumberOf(State.MARKED));
	}

	// @Test
	// public void openAdjacentTiles(){
	// Field field = new Field(ROWS, COLUMNS, MINES);
	// Tile tileOpen = field.getTile(4, 7);
	// tileOpen.setState(State.OPEN);
	// Tile tileMarked = field.getTile(6, 9);
	// tileMarked.setState(State.MARKED);
	// field.openAdjacentTiles(5,8);
	// assertEquals(State.OPEN,field.getTile(4,7).getState());
	// assertEquals(State.OPEN,field.getTile(4,8).getState());
	// assertEquals(State.OPEN,field.getTile(4,9).getState());
	// assertEquals(State.OPEN,field.getTile(5,7).getState());
	// assertEquals(State.OPEN,field.getTile(5,9).getState());
	// assertEquals(State.OPEN,field.getTile(6,7).getState());
	// assertEquals(State.OPEN,field.getTile(6,8).getState());
	// assertEquals(State.MARKED,field.getTile(6,9).getState());
	// }

	@Test
	public void countAdjacentMines() {
		Field field = new Field(ROWS, COLUMNS, MINES);

		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				if (field.getTile(row, column) instanceof Mine) {
				} else {
					int count = 0;
					if (row > 0 && column > 0)
						if (field.getTile(row - 1, column - 1) instanceof Mine) {
							count++;
						}
					if (column > 0)
						if (field.getTile(row, column - 1) instanceof Mine) {
							count++;
						}
					if (row < field.getRowCount() - 1 && column > 0)
						if (field.getTile(row + 1, column - 1) instanceof Mine) {
							count++;
						}
					if (row > 0 && column < field.getColumnCount() - 1)
						if (field.getTile(row - 1, column + 1) instanceof Mine) {
							count++;
						}
					if (column < field.getColumnCount() - 1)
						if (field.getTile(row, column + 1) instanceof Mine) {
							count++;
						}
					if (row < field.getRowCount() - 1 && column < field.getColumnCount() - 1)
						if (field.getTile(row + 1, column + 1) instanceof Mine) {
							count++;
						}
					if (row > 0)
						if (field.getTile(row - 1, column) instanceof Mine) {
							count++;
						}
					if (row < field.getRowCount() - 1)
						if (field.getTile(row + 1, column) instanceof Mine) {
							count++;
						}
					assertEquals(count, field.countAdjacentMines(row, column));
				}
			}
		}
	}
}
