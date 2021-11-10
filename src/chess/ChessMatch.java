package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
    private Board board;

    public ChessMatch() {
        this.board = new Board(8,8);
        this.initialSetup();
    }

    public ChessPiece[][] getPieces(){

        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i,j);
            }
        }
        return mat;
    }
    
    public boolean[][] possibleMoves(ChessPosition sourcePosition){
    	var position = sourcePosition.toPosition();
    	validateSourcePosition(position);
    	
    	return this.board.piece(position).possibleMoves();
    }
    
    public ChessPiece performChessMove(ChessPosition source,ChessPosition target) {
    	Position sourceP = source.toPosition();
    	Position targetP = target.toPosition();
    	
    	validateSourcePosition(sourceP);
    	validateTargetPosition(sourceP,targetP);
    	Piece capturedPiece = makeMove(sourceP,targetP);
    	return (ChessPiece)capturedPiece;
    }

	private void validateTargetPosition(Position source, Position target) {
		if (!this.board.piece(source).possibleMove(target)) {
			throw new ChessException("Invalid move");			
		}
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)){
			throw new ChessException("There no is piece on source position");
		}
		
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There no is possible move for the chosen piece");	
		}
				
	}
	
    private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}
	

	private void placeNewPiece(char column,int row,ChessPiece piece){
        this.board.placePiece(piece,new ChessPosition(column,row).toPosition());
    }
    private void initialSetup(){
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }
}
