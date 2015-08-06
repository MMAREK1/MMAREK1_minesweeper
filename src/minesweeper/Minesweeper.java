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
	BestTimes bestTimes=new BestTimes();

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
		userInterface = new ConsoleUI();
		Field field = new Field(20, 20, 30);
		userInterface.newGameStarted(field);
	}
	public static Minesweeper getInstance() {
		return instance;
	}
	public static int getPlayingSeconds(){
		int playTime=(int)(System.currentTimeMillis()-getStartMillis())/1000;
		return playTime;		
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