package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.UserInterface;
import minesweeper.core.Field;
import minesweeper.core.GameState;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
	/** Playing field. */
	private Field field;
	/** setting color */
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";

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
		System.out.println("Hello player " + System.getProperty("user.name"));
		do {
			update();
			processInput();
			if (field.getState() == GameState.SOLVED) {
				update();
				System.out.println("You win !!!");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.consoleui.UserInterface#update()
	 */
	
	//http://stackoverflow.com/questions/5757311/change-color-in-java-eclipse-console
	@Override
	public void update() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
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
			formatter.format("%4s", "\n");
		}
		System.out.println(sb);

	}

	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to input string.
	 */
	private void processInput() {
		String action = readLine();
		action = action.toUpperCase();
		if (action.length() == 1) {
			if (action.charAt(0) == 'X') {
				System.out.println("you end game");
				System.exit(0);
			}
		} else {

			Pattern pattern = Pattern.compile("([MO])([A-Z]+)([0-9]+)");
			Matcher matcher = pattern.matcher(action);
			if (matcher.matches()) {
				int column = Integer.parseInt(matcher.group(3));
				int row = 0;

				String strRow = matcher.group(2);
				int srLen = strRow.length() - 1;

				for (int i = srLen; i >= 0; i--) {
					int c = strRow.charAt(i) - 'A';
					row += (i == srLen) ? c : (c + 1) * Math.pow(26, srLen);
				}

				if ("M".equals(matcher.group(1))) {
					field.markTile(row, column);
				} else {
					field.openTile(row, column);
				}
			}
		}
	}
}