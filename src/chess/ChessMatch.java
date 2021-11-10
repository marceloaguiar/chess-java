package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;

	private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;    
    
    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> piecesCaptured = new ArrayList<>();

    public ChessMatch() {
        this.board = new Board(8,8);
        this.turn=1;
        this.currentPlayer=Color.WHITE;
        this.initialSetup();
    }
    
	public int getTurn() {
		return turn;
	}


	public Color getCurrentPlayer() {
		return currentPlayer;
	}
    
	public boolean getCheck() {
		return check;
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
    	
    	//Check if the play puts the player himself in check
    	if (testeCheck(currentPlayer)) {
    		undoMove(sourceP, targetP, capturedPiece);
    		throw new ChessException("Invalid move! You can't put yourself in check");		
    	}
    	
    	check = (testeCheck(opponent(currentPlayer)))?true:false;
    	
    	nextTurn();    	
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
		
		if (this.currentPlayer!= ((ChessPiece)this.board.piece(position)).getColor()){
			throw new ChessException("Invalid piece selected. You need choice your piece as a source");	
		}
		
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There no is possible move for the chosen piece");	
		}
				
	}
	
	private void nextTurn() {
		this.turn++;
		this.currentPlayer=(this.currentPlayer==Color.WHITE)?Color.BLACK:Color.WHITE;
	}
	
	private Color opponent(Color color) {
		return (color==Color.WHITE)?Color.BLACK:Color.WHITE;
	}
	
	private ChessPiece king (Color color) {
		var list = this.piecesOnTheBoard.stream().filter(x->((ChessPiece)x).getColor()==color).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + "King on the board");
	}
	
	private boolean testeCheck(Color color) {
		var kingPosition = this.king(color).getChessPosition().toPosition();
		var listOpponent = this.piecesOnTheBoard.stream().filter(x->((ChessPiece)x).getColor()==opponent(color)).collect(Collectors.toList());
		for (Piece piece : listOpponent) {
			var mat = piece.possibleMoves();
			boolean check = mat[kingPosition.getRow()][kingPosition.getColumn()];
			if (check) {return true;}
		}
		return false;
	}
	
    private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		if (capturedPiece!=null) {
			this.piecesOnTheBoard.remove(capturedPiece);
			this.piecesCaptured.add(capturedPiece);
		}
		
		return capturedPiece;
	}
    
    private void undoMove(Position source, Position target,Piece capturedPiece) {
    	Piece p = board.removePiece(target);
    	board.placePiece(p, source);
    	
    	if (capturedPiece!=null) {
    		board.placePiece(capturedPiece, target);
    		this.piecesOnTheBoard.add(capturedPiece);
    		this.piecesCaptured.remove(capturedPiece);
    	}
    }
	

	private void placeNewPiece(char column,int row,ChessPiece piece){
        this.board.placePiece(piece,new ChessPosition(column,row).toPosition());
        this.piecesOnTheBoard.add(piece);
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
