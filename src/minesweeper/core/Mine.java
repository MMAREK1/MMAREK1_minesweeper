package minesweeper.core;

/**
 * Mine tile.
 */
public class Mine extends Tile {
	
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
		if (this.getState() == State.OPEN)
			return "* ";
		else
			return super.toString();
    }
}
