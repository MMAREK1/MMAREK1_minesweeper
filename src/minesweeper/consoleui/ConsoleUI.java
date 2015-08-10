package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.BestTimes;
import minesweeper.Minesweeper;
import minesweeper.UserInterface;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Tile.State;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
	private BestTimes bestTimes = new BestTimes();
	/** Playing field. */
	private Field field;

	/** Input reader. */
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.consoleui.UserInterface#newGameStarted(minesweeper.core.
	 * Field)
	 */

	@Override
	public void newGameStarted(Field field) {
		this.field = field;
		String name = System.getProperty("user.name");
		System.out.println("Hello player " + name);
		do {
			update();
			processInput();
			if (field.getState() == GameState.SOLVED) {
				update();
				bestTimes.addPlayerTime(name, (int)(System.currentTimeMillis()-Minesweeper.getStartMillis()));
				System.out.println("You win !!!");
				System.out.println(bestTimes.toString());
				System.exit(0);
				
			}
			if (field.getState() == GameState.FAILED) {
				field.openMine();
				update();
				System.out.println("You lose !!!");
				System.exit(0);
			}
		} while (true);
	}

	public int getRemainingMineCount() {
		int remaining = field.getMineCount() - field.getNumberOf(State.MARKED);
		return remaining;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.consoleui.UserInterface#update()
	 */


	@Override
	public void update() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		formatter.format("%s", "Number of remaining mine:");
		formatter.format("%4s", getRemainingMineCount());
		formatter.format("%s", "      Time:");
		Minesweeper.getInstance();
		formatter.format("%4s\n", Minesweeper.getPlayingSeconds());
		formatter.format("%4s", " ");
		for (int i = 0; i < field.getColumnCount(); i++) {
			formatter.format("%4s", i);
		}
		formatter.format("%4s", "\n");

		for (int row = 0; row < field.getRowCount(); row++) {
			String rowLabel = "";

			if (row < 26) {
				formatter.format("%4c", row + 65);
			} else {
				int rowNumber = row;
				rowLabel = Character.toString((char) (Math.floor(rowNumber / 26) + 64))
						+ Character.toString((char) (rowNumber % 26 + 65));
				formatter.format("%4s", rowLabel);
			}

			for (int column = 0; column < field.getColumnCount(); column++) {
				formatter.format("%4s", field.getTile(row, column));
			}
			if (row < 26) {
				formatter.format("%4c", row + 65);
			} else {
				int rowNumber = row;
				rowLabel = Character.toString((char) (Math.floor(rowNumber / 26) + 64))
						+ Character.toString((char) (rowNumber % 26 + 65));
				formatter.format("%4s", rowLabel);
			}
			formatter.format("%4s", "\n");
		}
		formatter.format("%4s", " ");
		for (int i = 0; i < field.getColumnCount(); i++) {
			formatter.format("%4s", i);
		}
		System.out.println(sb);
		formatter.close();
	}

	void handleInput(String input) throws WrongFormatException {
		if (input.length() == 1) {
			if (input.charAt(0) == 'X') {
				System.out.println("you end game");
				System.exit(0);
			}
		} else {

			Pattern pattern = Pattern.compile("([MO])([A-Z]+)([0-9]+)");
			Matcher matcher = pattern.matcher(input);
			if (matcher.matches()) {
				int column = Integer.parseInt(matcher.group(3));
				int row = 0;

				String strRow = matcher.group(2);
				int srLen = strRow.length() - 1;

				for (int i = srLen; i >= 0; i--) {
					int c = strRow.charAt(i) - 'A';
					row += (i == srLen) ? c : (c + 1) * Math.pow(26, srLen);
				}
				if (row >= field.getRowCount() || column >= field.getColumnCount()) {
					throw new WrongFormatException("Out of field");
				}
				if ("M".equals(matcher.group(1))) {
					field.markTile(row, column);
				} else {
					field.openTile(row, column);
				}
			} else {
				throw new WrongFormatException("Wrong Input");
			}
		}
	}

	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to input string.
	 */
	private void processInput() {
		String action = readLine();
		action = action.toUpperCase();
		try {
			handleInput(action);
		} catch (WrongFormatException ex) {
			System.out.println(ex.getMessage());
		}
	}
}