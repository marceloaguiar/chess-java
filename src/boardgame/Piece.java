package boardgame;

import java.util.Iterator;

public abstract class Piece {
    protected Position position;
    private Board board;

    public Piece(Board board) {
        this.board = board;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    protected Board getBoard() {
        return board;
    }
    
    /* 
     * abstract because need to be implemented in each piece
     * since each piece has its rules
     */
    public abstract boolean[][] possibleMoves();
    
    public boolean possibleMove(Position position) {
    	return possibleMoves()[position.getRow()][position.getColumn()];
    }
    
    public boolean isThereAnyPossibleMove() {
    	var move = possibleMoves();    	
    	for (int i = 0; i < move.length; i++) {
			for (int j = 0; j < move.length; j++) {
				if (move[i][j]) {
					return true;
				}
			}
		}
    	return false;
    }
    
}
