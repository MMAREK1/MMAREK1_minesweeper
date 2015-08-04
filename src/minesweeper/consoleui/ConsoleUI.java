package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.UserInterface;
import minesweeper.core.Field;
import minesweeper.core.GameState;

/**
 * Console user interface.
 */
public class ConsoleUI implements  UserInterface {
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
    /* (non-Javadoc)
	 * @see minesweeper.consoleui.UserInterface#newGameStarted(minesweeper.core.Field)
	 */

	@Override
	public void newGameStarted(Field field) {
        this.field = field;
        do {
            update();
            processInput();
            if(field.getState() == GameState.SOLVED){
            	System.out.println("Vyhral si");
            	System.exit(0);
            }
            if(field.getState() == GameState.FAILED)
            {
            	System.out.println("Prehral si");
            	System.exit(0);
            }
        } while(true);
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
        String action = readLine();
        action=action.toUpperCase();
        if(action.length()==1)
        {
        	if(action.charAt(0)=='X')
        	{
        		System.out.println("you end game");
        		System.exit(0);
        	}
        }else
        {

        	Pattern pattern = Pattern.compile("([MO])([A-J])([0-9])");
        	Matcher matcher = pattern.matcher(action);
        	if(matcher.matches())
        	{
        		int column=Integer.parseInt(matcher.group(3));
        		int row=matcher.group(2).charAt(0)-'A';
        		if(matcher.group(1).charAt(0)=='M')
        		{
        			field.markTile(row,column);
        		}
        		else
        		{
        			field.openTile(row,column);
        		}
        	}
        }
    }
}