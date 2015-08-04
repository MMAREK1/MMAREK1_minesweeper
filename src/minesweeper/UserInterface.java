package minesweeper;

import minesweeper.core.Field;

public interface UserInterface {

	/* (non-Javadoc)
	 * @see minesweeper.consoleui.UserInterface#newGameStarted(minesweeper.core.Field)
	 */
	void newGameStarted(Field field);

	/* (non-Javadoc)
	 * @see minesweeper.consoleui.UserInterface#update()
	 */
	void update();

}