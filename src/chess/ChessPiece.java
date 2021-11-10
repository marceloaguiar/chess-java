package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

    private Color color;
    private int moveCount;
    
    public ChessPiece(Board board,Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    
    public int getMoveCount() {
        return moveCount;
    }
    
    public ChessPosition getChessPosition() {
    	return ChessPosition.fromPosition(this.position);    	
    }
    
    protected boolean isThereOpponentPiece(Position position) {
    	var piece = (ChessPiece)this.getBoard().piece(position);
    	return (piece!=null && piece.color != this.color);    	
    }
    
    public void increaseMoveCount() {
    	this.moveCount++;
    }
    
    public void decreaseMoveCount() {
    	this.moveCount--;	
    }    
}
