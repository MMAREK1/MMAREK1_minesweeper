package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.UserInterface;
import minesweeper.core.Field;
import sun.security.jca.GetInstance.Instance;

/**
 * Main application class.
 */
public class Minesweeper {
	/** User interface. */
	private UserInterface userInterface;
	private static long startMillis;
	private static Minesweeper instance;
	private BestTimes bestTimes = new BestTimes();

	public BestTimes getBestTimes() {
		return bestTimes;
	}

	public static long getStartMillis() {
		return startMillis;
	}

	public static void setStartMillis() {
		startMillis = System.currentTimeMillis();
	}

	/**
	 * Constructor.
	 */
	private Minesweeper() {
		instance = this;
		startMillis = getStartMillis();
		userInterface = new ConsoleUI();
		Field field = new Field(20, 20, 30);
		bestTimes.addPlayerTime("Fero", 145);
		bestTimes.addPlayerTime("Jano", 45);
		System.out.println(bestTimes.toString());
		startMillis = getStartMillis();
		userInterface.newGameStarted(field);
	}

	public static Minesweeper getInstance() {
		return instance;
	}

	public static int getPlayingSeconds() {
		return (int) (System.currentTimeMillis() - startMillis) / 1000;
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            arguments
	 */
	public static void main(String[] args) {
		setStartMillis();
		new Minesweeper();
	}
}