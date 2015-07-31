package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import minesweeper.UserInterface;
import minesweeper.core.Field;
import minesweeper.core.Tile;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    /** Playing field. */
    private Field field;
    
    /** Input reader. */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Reads line of text from the reader.
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }
    
    /* (non-Javadoc)
	 * @see minesweeper.consoleui.UserInterface#newGameStarted(minesweeper.core.Field)
	 */
    @Override
	public void newGameStarted(Field field) {
        this.field = field;
        do {
            update();
            processInput();
            //throw new UnsupportedOperationException("Resolve the game state - winning or loosing condition.");
        } while(false);
    }
    
    /* (non-Javadoc)
	 * @see minesweeper.consoleui.UserInterface#update()
	 */
    @Override
	public void update() {
    	System.out.printf("%4s"," ");
    	for(int i=0;i<field.getColumnCount();i++){
    			System.out.printf("%4s",i);
    	}
    	System.out.println();
    		
    	for (int row = 0; row < field.getRowCount(); row++) {
    		System.out.printf("%4c",row+65);
			for (int column = 0; column < field.getColumnCount(); column++) {
				System.out.printf("%4s",field.getTile(row,column));
			}
			System.out.println();
		}
    
    }
    
    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        //throw new UnsupportedOperationException("Method processInput not yet implemented");
    }
}
